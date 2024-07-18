<template>
	<section>
		<!--工具条-搜索区域-->
		<el-col :span="24" class="toolbar" style="padding-bottom: 0px;">
			<el-form :inline="true" :model="queryFrom">
				<el-form-item>
					<el-input v-model="queryFrom.keyword" placeholder="部门名称"></el-input>
				</el-form-item>
				<el-form-item>
					<el-button type="primary" v-on:click="getDepartment">查询</el-button>
				</el-form-item>
				<el-form-item>
					<el-button type="primary" @click="handleAdd">新增</el-button>
				</el-form-item>
			</el-form>
		</el-col>

		<!--列表-数据区域-->
		<el-table :data="list" highlight-current-row v-loading="listLoading" @selection-change="selsChange" style="width: 100%;">
			<el-table-column type="selection" width="55">
			</el-table-column>
			<el-table-column type="index" width="60">
			</el-table-column>
			<el-table-column prop="name" label="姓名" width="120" sortable>
			</el-table-column>


      <el-table-column prop="intro" label="简介" width="120" sortable>
      </el-table-column>
      <el-table-column prop="createTime" label="创建时间" width="180" sortable>
      </el-table-column>
      <el-table-column prop="updateTime" label="更新时间" width="180" sortable>
      </el-table-column>
      <el-table-column prop="manager.username" label="主管名称" width="120" sortable>
      </el-table-column>
      <el-table-column prop="parent.name" label="上级部门" width="120" sortable>
      </el-table-column>
      <el-table-column prop="path" label="路径" width="120" sortable>
      </el-table-column>
      <el-table-column prop="state" label="状态" width="120" sortable>
      </el-table-column>




			<el-table-column label="操作" width="150">
				<template scope="scope">
					<el-button size="small" @click="handleEdit(scope.$index, scope.row)">编辑</el-button>
					<el-button type="danger" size="small" @click="handleDel(scope.$index, scope.row)">删除</el-button>
				</template>
			</el-table-column>
		</el-table>

		<!--工具条-批量删除和分页-->
		<el-col :span="24" class="toolbar">
			<el-button type="danger" @click="batchRemove" :disabled="this.sels.length===0">批量删除</el-button>
<!--			<el-pagination layout="prev, pager, next" @current-change="handleCurrentChange" :page-size="20" :total="total" style="float:right;">-->
<!--			</el-pagination>-->
      <!--
       @size-change : 当你选择每页条数改变的时候触发的方法
       @current-change: 当前页数改变的时候的触发的方法
       :current-page: 当前页数
       :page-sizes: 你可以设置 每页条数
       :page-size: 就是每页的条数
       :total: 总条数
       style="float:right; 浮动像右
      -->
      <el-pagination
          style="float:right;"
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
          :current-page=this.queryFrom.currentPage
          :page-sizes="[3, 5, 10, 20]"
          :page-size=this.queryFrom.pageSize
          layout="total, sizes, prev, pager, next, jumper"
          :total=this.total>
      </el-pagination>
		</el-col>

		<!--编辑界面-->
		<el-dialog title="新增/编辑"  :visible.sync="saveFormVisible" :close-on-click-modal="false">
			<el-form :model="saveForm" label-width="80px" :rules="saveFormRules" ref="saveForm">
				<el-form-item label="部门名称" prop="name">
					<el-input v-model="saveForm.name" auto-complete="off"></el-input>
				</el-form-item>
        <el-form-item label="部门介绍" prop="intro">
          <el-input v-model="saveForm.intro" auto-complete="off"></el-input>
        </el-form-item>
        <el-form-item label="部门主管" prop="manager.username">
          <el-select v-model="saveForm.manager" clearable value-key="id" placeholder="请选择">
            <!--下拉选项
              :key=""  //唯一标识  等于原来的  id属性
              :label 选择之后展示到选择框中的值
              :value 选中之后绑定给模型层的值  如果要绑定对象给模型层 有一个大坑
              必须要写  value-key="id"
            -->
            <el-option
                v-for="item in employees"
                :key="item.id"
                :label="item.username"
                :value="item">
              <span style="float: left">{{ item.username }}</span>
              <span style="float: right; color: #8492a6; font-size: 13px">{{ item.email }}</span>
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="上级部门" prop="parent.name">
          <!--
            label:'name', label是显示的值 如果你要显示其他的属性 那么就通过label属性去定义
            value:是当你选择某一个值的时候 就会帮你拿到这个数据的值
          -->
          <el-cascader v-model="saveForm.parent"
                       :options="deptTree"
                       :props="{
							   checkStrictly: true,
							   label:'name',
							   value:'id'
							}"
              clearable></el-cascader>
        </el-form-item>
        <el-form-item label="状态" prop="status">
          <el-input v-model="saveForm.state" auto-complete="off"></el-input>
        </el-form-item>

			</el-form>
			<div slot="footer" class="dialog-footer">
				<el-button @click.native="saveFormVisible = false">取消</el-button>
				<el-button type="primary" @click.native="saveSubmit" :loading="editLoading">提交</el-button>
			</div>
		</el-dialog>


	</section>
</template>

<script>

	export default {
		data() {
			return {
			  queryFrom:{
			    pageSize:10,
          currentPage:1,
          keyword:""
        },
				filters: {
					name: ''
				},
        list: [],
				total: 0,
				page: 1,
				listLoading: false,
				sels: [],//列表选中列

				saveFormVisible: false,//编辑界面是否显示
				editLoading: false,
				saveFormRules: {
					name: [
						{ required: true, message: '请输入部门名称', trigger: 'blur' }
					]
				},
				//编辑界面数据
				saveForm: {
					id:null,
          intro:"",
          updateTime:"",
          createTime:"",
          name:"",
          manager:{
					  id:null,
            username:""
          },
          parent:{
					  id:null,
            name:""
          },
          state:""
				},
        employees:[],
        deptTree:[]

			}
		},
		methods: {
      // 查询部门树
      getDeptTree(){
        this.$http.get("/department/deptTree")
            .then(res=>{
              res = res.data // 拿到AjaxResult
              if (res.success){
                this.deptTree = res.data
              }
            })
            .catch(res=>{})
      },
		  // 查询所有的主管
      getManagers(){
        this.$http.get("/employee")
        .then(res=>{
          res = res.data // 拿到AjaxResult
          if (res.success){
            this.employees = res.data
          }
        })
        .catch(res=>{})
      },
		  //handleSizeChange
      handleSizeChange(val){
        //  这个方法是 当你在设置页面条数的时候会被触发  val是你选择每页的条数
        this.queryFrom.pageSize = val;
        this.getDepartment()
      },
			handleCurrentChange(val) {
        this.queryFrom.currentPage = val;
				this.getDepartment();
			},
			//获取部门列表
			getDepartment() {

				this.listLoading = true; // 显示那个 加载的遮罩层
        // 发送请求 请求后台
        this.$http.post("/department",this.queryFrom).then(res=>{
          // console.log(res)
          res = res.data.data
          this.list = res.list
          this.total = res.total
          console.log(res)
          this.listLoading = false;
        }).catch(res=>{
        })
			},
			//删除
			handleDel: function (index, row) {
				this.$confirm('确认删除该记录吗?', '提示', {
					type: 'warning'
				}).then(() => {
					// 发送请求
          this.$http.delete("/department/"+row.id)
          .then(res=>{
            if (res.data.success){
              this.$message({type:"success",message:"删除成功"})
              this.queryFrom.currentPage = 1;
              this.getDepartment()
            }
          }).catch(()=>{})
				}).catch(() => {

				});
			},
			//显示编辑界面
			handleEdit: function (index, row) {
				this.saveFormVisible = true;

        row = Object.assign({}, row)
				this.saveForm = row
        if (!row.parent){
          this.saveForm.parent = {id:null,name:""}
        }
        if (!row.manager){
          this.saveForm.manager = {id:null,username:""}
        }
        // 判断只要 saveForm.parent.id 不为null
        var parenId = []
        if (this.saveForm.parent.id){
          console.log("path",this.saveForm.path)
          var ids = this.saveForm.path.split("/")
          console.log(ids)
          for (let i = 1; i < ids.length-1; i++) {
            parenId.push(Number.parseInt(ids[i]))
          }
        }
        // 级联框回显数据是通过数据的 找到每一个对象的 id 就可以回显了
        this.saveForm.parent = parenId // [1,2]
        this.getManagers()
        this.getDeptTree();
			},
			//显示新增界面
			handleAdd: function () {
        this.saveFormVisible = true;
				this.saveForm={
          id:null,
          intro:"",
          updateTime:"",
          createTime:"",
          name:"",
          manager:{
            id:null,
            username:""
          },
          parent:{
            id:null,
            name:""
          },
          state:""
        }
        this.getManagers()
        this.getDeptTree()
			},
			// 新增和修改
			saveSubmit: function () {
				this.$refs.saveForm.validate((valid) => {
					if (valid) {
						this.$confirm('确认提交吗？', '提示', {}).then(() => {
              // 发送后台请求
              // 处理上级部门已经变成数组的问题
              // console.log(this.saveForm.parent)
              if (this.saveForm.parent.length > 0){
                this.saveForm.parent = {
                  id:this.saveForm.parent[this.saveForm.parent.length-1],
                  name:""
                }
              }else{
                this.saveForm.parent = {
                  id:null,
                  name:""
                }
              }
              // console.log(this.saveForm.parent)
              // return ;
              // 发送请求
              this.$http.put("/department",this.saveForm)
                  .then(res=>{
                    if (res.data.success){
                      this.$message({type:"success",message:"保存成功"})
                      this.saveFormVisible = false;
                      this.queryFrom.currentPage = 1;
                      this.getDepartment()
                    }

                  }).catch(()=>{})
						});
					}
				});
			},

			selsChange: function (sels) {
				this.sels = sels;
			},
			//批量删除
			batchRemove: function () {
				var ids = this.sels.map(item => item.id);
				this.$confirm('确认删除选中记录吗？', '提示', {
					type: 'warning'
				}).then(() => {
          this.$http.patch("/department",ids)
              .then(res=>{
                if (res.data.success){
                  this.$message({type:"success",message:"删除成功"})
                  this.queryFrom.currentPage = 1;
                  this.getDepartment()
                }
              }).catch(()=>{})
				}).catch(() => {

				});
			}
		},
		mounted() {
			this.getDepartment();
		}
	}

</script>

<style scoped>

</style>