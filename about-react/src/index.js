// 1. 导入 React 组件
import React from 'react' // 创建组件、虚拟DOM元素，生命周期
import ReactDOM from 'react-dom' // 把创建好的 组件 和 虚拟DOM 渲染到页面上

// 导入 Hello 组件
// 默认，如果不做单独的配置的话，不能省略 .jsx 后缀名
import Hello from './components/Hello.jsx'

// import './class-test.js'
import './class-extends.js'

// 2. 创建虚拟DOM元素
// 参数1：创建的元素的类型，字符串，表示元素的名称
// 参数2：是一个对象或者 null，表示 当前这个 DOM 元素的属性
// 参数3：子节点
// 参数n：其他子节点
// <h1 id="myh1" title="this is a h1">这是 H1 标题</h1>
const myh1 = React.createElement('h1', { id: "myh1", title : "this is a h1"}, '这是 H1 标题')

const mydiv = React.createElement('div', null, '这是一个div元素', myh1)

const mydiv2 = <div id="mydiv2" title="div react">
    这是一个div元素
    <h1>这是一个嵌套的H1</h1>
    </div>
// 3. 使用 ReactDOM 把虚拟 DOM 渲染到页面上
// 参数1: 要渲染的那个虚拟 DOM 元素
// 参数2：指定页面上DOM元素，当作容器
// Target container is not a DOM element.
// 经过分析，猜测：第二个参数接收的应该是一个 DOM 元素而不是选择器
// ReactDOM.render(mydiv2, document.getElementById('app'))

let a = 10
let str = '你好，中国'
let boo = true
let title='999'
const h1 = <h1>红红火火</h1>
const arr = [
    <h2>这是h2</h2>,
    <h3>这是h3</h3>
]
const arrStr = ['毛利兰', '柯南', '小五郎', '灰原哀']

// 定义一个空数组，用来存放 arrStr 标签
const nameArr = []
arrStr.forEach(item => {
    const temp = <h5 key={item}>{item}</h5>
    nameArr.push(temp)
})

// 什么情况下需要使用 {} ？
// 答：当我们需要在 JSX 控制的区域内，写 JS 表达式了，则需要把 JS 代码写到 {} 中
// ReactDOM.render(<div>
//     {a + 2}
//     <hr />
//     {str}
//     <hr />
//     {boo ? '条件为真' : '条件为假'}
//     <hr />
//     <p title={title}>这是 p 标签</p>
//     {h1}
//     <hr />
//     {/* {arr} */}
//     <hr />
//     {nameArr}
//     <hr />
//     {arrStr.map(item => {
//         return <h3 key={item}>{item}</h3>
//     }) }
//     </div>, document.getElementById('app')
//     )



const dog = {
    name: '大黄',
    age: 3,
    gender: '雄'
}
// ReactDOM.render(<div>
//     Hello World
//     {/* 直接把 组件的名称,以标签格式,丢到页面上即可 */}
//     {/* <Hello name={dog.name} age={dog.age} gender={dog.gender}></Hello> */}
//     <Hello {...dog}></Hello>
// </div>, document.getElementById('app')
// )


class Movie extends React.Component {
    render() {
        return <div>
            这是 Movie 组件 -- {this.props.name} -- {this.props.age}
        </div>
    }
}

// ReactDOM.render(<div>
//          Hello World
//          <Movie></Movie>
//          <hr />
//          <Movie {...dog}></Movie>
//      </div>, document.getElementById('app')
//     )



import CmtListView from './components/CmtList'
// ReactDOM.render(<div>
//     <CmtListView></CmtListView>
// </div>, document.getElementById('app')
// )
import BindEvent from '@/components/BindEvent'

ReactDOM.render(<div>
    <hr />
    <BindEvent></BindEvent>
</div>, document.getElementById('app')
)
