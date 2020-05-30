package j2ee.jdbc.derby;

import javax.sql.RowSet;
import javax.sql.rowset.CachedRowSet;
import javax.sql.rowset.RowSetFactory;
import javax.sql.rowset.RowSetProvider;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Properties;

import static j2ee.jdbc.derby.Util.getConnection;

/**
 * 描述:  <br>
 *
 * @author: skilled-peon <br>
 * @date: 2020/5/30 0030 <br>
 */
public class ViewDBFrame extends JFrame {

    private JComboBox<String> tableNames;
    private CachedRowSet crs;
    private Component scrollPane;
    private DataPanel dataPanel;

    private JButton previousButton;
    private JButton nextButton;
    private JButton deleteButton;
    private JButton saveButton;

    public ViewDBFrame() {
        tableNames = new JComboBox<>();
        tableNames.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    showTable((String) tableNames.getSelectedItem());
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });
        add(tableNames, BorderLayout.NORTH);

        try {
            try (Connection conn = getConnection()) {
                DatabaseMetaData meta = conn.getMetaData();
                ResultSet mrs = meta.getTables(null, null, null, new String[]{"TABLE"});
                while (mrs.next()) {
                    tableNames.addItem(mrs.getString(3));
                }
            }
        } catch (SQLException | IOException ex) {
            JOptionPane.showMessageDialog(this, ex);
        }

        // 按钮布局
        JPanel buttonPanel = new JPanel();
        add(buttonPanel, BorderLayout.SOUTH);
        // 上一条按钮
        previousButton = new JButton("Previous");
        previousButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showPreviousRow();
            }
        });
        buttonPanel.add(previousButton);

        //下一条按钮
        nextButton = new JButton("Next");
        nextButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showNextRow();
            }
        });
        buttonPanel.add(nextButton);

        // 删除当前行按钮
        deleteButton = new JButton("Delete");
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    deleteRow();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });
        buttonPanel.add(deleteButton);

        // 保存当前行按钮
        saveButton = new JButton("Save");
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    saveChanges();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });
        buttonPanel.add(saveButton);
        // 自适应
        pack();


    }

    public void showTable(String tableName) throws IOException {
        try {
            try (Connection conn = getConnection()) {
                // get result set
                Statement statement = conn.createStatement();
                ResultSet rs = statement.executeQuery("SELECT * FROM " + tableName);
                // copy into cached row set
                RowSetFactory factory = RowSetProvider.newFactory();
                crs = factory.createCachedRowSet();
                crs.setTableName(tableName);
                crs.populate(rs);
            }

            if (scrollPane != null)
                remove(scrollPane);

            dataPanel = new DataPanel(crs);
            scrollPane = new JScrollPane(dataPanel);
            add(scrollPane, BorderLayout.CENTER);
            validate();
            showNextRow();

        } catch (SQLException e) {
             JOptionPane.showMessageDialog(this, e);
        }
    }

    public void showPreviousRow() {
        try {
            if (crs == null || crs.isFirst())
                return;

            crs.previous();
            dataPanel.showRow(crs);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, e);
        }
    }

    /**
     * 删除当前行的数据
     * @throws IOException
     */
    public void deleteRow() throws IOException {
        try {
            try (Connection conn = getConnection()) {
                crs.deleteRow();
                crs.acceptChanges(conn);
                if (crs.isAfterLast())
                    if (!crs.last()) crs = null;
                dataPanel.showRow(crs);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, ex);
        }
    }

    public void saveChanges() throws IOException {
        try {
            try (Connection conn = getConnection()) {
                dataPanel.setRow(crs);
                crs.acceptChanges(conn);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, ex);
        }
    }

    public void showNextRow() {
        try {
            if (crs == null || crs.isLast())
                return;

            crs.next();
            dataPanel.showRow(crs);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, e);
        }
    }


    /**
     * 数据看板
     */
    class DataPanel extends JPanel {
        private java.util.List<JTextField> fields;

        public DataPanel(RowSet rs) throws SQLException {
            fields = new ArrayList<>();
            setLayout(new GridBagLayout());
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.gridwidth = 1;
            gbc.gridheight = 1;

            ResultSetMetaData rsmd = rs.getMetaData();
            for (int i = 1; i <= rsmd.getColumnCount(); i++) {
                gbc.gridy = i - 1;
                String columnName = rsmd.getColumnLabel(i);
                gbc.gridx = 0;
                gbc.anchor = GridBagConstraints.EAST;
                add(new JLabel(columnName), gbc);

                int columnWidth = rsmd.getColumnDisplaySize(i);
                JTextField tb = new JTextField(columnWidth);
                if (!rsmd.getColumnClassName(i).equals("java.lang.String")) {
                    tb.setEditable(false);
                }

                fields.add(tb);

                gbc.gridx = 1;
                gbc.anchor = GridBagConstraints.WEST;
                add(tb, gbc);
            }
        }

        /**
         * show database rows by populating all text field with the column values
         * @param rs 结果集
         * @throws SQLException
         */
        public void showRow(ResultSet rs) throws SQLException {
            for (int i = 1; i <= fields.size(); i++) {
                String field = rs == null ? "" : rs.getString(i);
                JTextField tb = fields.get(i - 1);
                tb.setText(field);
            }
        }

        /**
         * 更新数据到行集的当前行
         * @param rs
         * @throws SQLException
         */
        public void setRow(RowSet rs) throws SQLException {
            for (int i = 1; i <= fields.size(); i++) {
                String field = rs.getString(i);
                JTextField tb = fields.get(i - 1);
                if (!field.equals(tb.getText())) {
                    rs.updateString(i, tb.getText());
                }
            }
            rs.updateRow();
        }
    }
}
