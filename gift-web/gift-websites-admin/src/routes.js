import Login from './views/Login.vue'
import RegShop from './views/ShopRegister.vue'
import NotFound from './views/404.vue'
import Home from './views/Home.vue'
import Main from './views/Main.vue'
import Table from './views/nav1/Table.vue'
import Form from './views/nav1/Form.vue'
import user from './views/nav1/user.vue'
import Page4 from './views/nav2/Page4.vue'
import Page5 from './views/nav2/Page5.vue'
import Page6 from './views/nav3/Page6.vue'
import echarts from './views/charts/echarts.vue'
import CarType from './views/car/CarType'
import Car from './views/car/Car'
//=====================导入自定义组件|===============================
import Department from "./views/org/department/Department";
import Employee from "./views/org/employee/Employee";
import Permission from "./views/auth/permission/Permission";
import Role from "./views/auth/role/Role";
import NoPermission from "./views/NoPermission"
//=====================导入自定义组件===============================

let routes = [
    {
        path: '/',
        component: Home,
        name: 'Charts',
        iconCls: 'fa fa-bar-chart',
        leaf: true,//只有一个节点
        children: [
            { path: '/echarts', component: echarts, name: '首页' },
            {path: '/noPermission', component: NoPermission, name: '', hidden: true },
        ]
    },
    {
        path: '/login',
        component: Login,
        name: '',
        hidden: true
    },
    {
        path: '/registerShop',
        component: RegShop,
        name: '',
        hidden: true
    },
    {
        path: '/404',
        component: NotFound,
        name: '',
        hidden: true
    },

    {
        path: '/',
        component: Home,
        name: '组织机构',
        iconCls: 'fa fa-id-card-o',
        children: [
            { path: '/department', component: Department, name: '部门管理' },
            { path: '/employee', component: Employee, name: '员工管理' },
            { path: '/page5', component: Page5, name: '页面5' }
        ]
    },
    {
        path: '/',
        component: Home,
        name: '权限管理',
        iconCls: 'fa fa-id-card-o',
        children: [
            { path: '/permission', component: Permission, name: '权限管理' },
            { path: '/role', component: Role, name: '角色管理' },
        ]
    },
    {
        path: '/',
        component: Home,
        name: '车辆管理',
        iconCls: 'fa fa-id-card-o',
        children: [
            { path: '/carType', component: CarType, name: '车辆类型' },
            { path: '/car', component: Car, name: '车辆管理' }
        ]
    },
    //{ path: '/main', component: Main },
    {
        path: '/',
        component: Home,
        name: '导航一',
        iconCls: 'el-icon-message',//图标样式class
        children: [
            { path: '/main', component: Main, name: '主页', hidden: true },
            { path: '/table', component: Table, name: 'Table' },
            { path: '/form', component: Form, name: 'Form' },
            { path: '/user', component: user, name: '列表' },
        ]
    }, {
        path: '/',
        component: Home,
        name: '导航二',
        iconCls: 'fa fa-id-card-o',
        children: [
            { path: '/page4', component: Page4, name: '页面4' },
            { path: '/page5', component: Page5, name: '页面5' }
        ]
    },
    {
        path: '/',
        component: Home,
        name: '',
        iconCls: 'fa fa-address-card',
        leaf: true,//只有一个节点
        children: [
            { path: '/page6', component: Page6, name: '导航三' }
        ]
    },


    {
        path: '*',
        hidden: true,
        redirect: { path: '/404' }
    }
];

export default routes;