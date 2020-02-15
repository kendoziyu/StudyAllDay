// 1. 导入 React 组件
import React from 'react' // 创建组件、虚拟DOM元素，生命周期
import ReactDOM from 'react-dom' // 把创建好的 组件 和 虚拟DOM 渲染到页面上

// 2. 创建虚拟DOM元素
// 参数1：创建的元素的类型，字符串，表示元素的名称
// 参数2：是一个对象或者 null，表示 当前这个 DOM 元素的属性
// 参数3：子节点
// 参数n：其他子节点
// <h1 id="myh1" title="this is a h1">这是 H1 标题</h1>
const myh1 = React.createElement('h1', { id: "myh1", title : "this is a h1"}, '这是 H1 标题')

// 3. 使用 ReactDOM 把虚拟 DOM 渲染到页面上
// 参数1: 要渲染的那个虚拟 DOM 元素
// 参数2：指定页面上DOM元素，当作容器
// Target container is not a DOM element.
// 经过分析，猜测：第二个参数接收的应该是一个 DOM 元素而不是选择器
ReactDOM.render(myh1, document.getElementById('app'))