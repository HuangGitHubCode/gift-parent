<template>
	<section>
		<!--工具条 - 高级查询框-->
		<el-col :span="24" class="toolbar" style="padding-bottom: 0px;">
			<el-form :inline="true" :model="query">
				<el-form-item>
					<el-input v-model="query.keyword" placeholder="请输入关键字"></el-input>
				</el-form-item>
				<el-form-item>
					<el-button type="primary" v-on:click="search">查询</el-button>
				</el-form-item>
				<el-form-item>
					<el-button type="primary" @click="handleAdd">新增</el-button>
				</el-form-item>
				<el-form-item>
					<el-button type="success" @click="onSale" :disabled="this.sels.length===0">上架</el-button>
				</el-form-item>
				<el-form-item>
					<el-button type="danger" @click="handleAdd" :disabled="this.sels.length===0">下架</el-button>
				</el-form-item>
			</el-form>
		</el-col>

		<!--列表-->
		<el-table :data="pageInfo.rows" highlight-current-row v-loading="listLoading" height="650"
              @selection-change="selsChange" style="width: 100%;">
			<el-table-column type="selection" width="55" fixed>
			</el-table-column>
			<el-table-column type="index" width="60" fixed>
			</el-table-column>
			<el-table-column prop="title" label="名称" fixed width="120" sortable>
			</el-table-column>
      <el-table-column prop="cover" label="封面图" width="120" sortable>
        <template slot-scope="scope">
            <img :src="scope.row.cover" width="80px" >
        </template>
      </el-table-column>
			<el-table-column prop="saleprice" label="原价" width="100" sortable>
			</el-table-column>
			<el-table-column prop="costprice" label="折扣价" width="100" sortable>
			</el-table-column>
			<el-table-column prop="isnew" label="是否新车" width="120" sortable>
        <template slot-scope="scope">
            <span v-if="scope.isnew == 1">是</span>
            <span v-else>否</span>
        </template>
			</el-table-column>
      <el-table-column prop="reigstertime" label="上牌时间" width="100" sortable>
      </el-table-column>
      <el-table-column prop="mileage" label="里程数" width="100" sortable>
      </el-table-column>
      <el-table-column prop="shopname" label="店铺名称" width="100" sortable>
      </el-table-column>
      <el-table-column prop="onsaletime" label="上架时间" width="100" sortable>
      </el-table-column>
      <el-table-column prop="offsaletime" label="下架时间" width="100" sortable>
      </el-table-column>
      <el-table-column prop="state" label="状态" min-width="80" sortable>
        <template slot-scope="scope">
          <span v-if="scope.row.state == 1" style="color: blue">上架</span>
          <span v-else>下架</span>
        </template>
      </el-table-column>
      <el-table-column prop="costeffective" label="超值" min-width="80" sortable>
        <template slot-scope="scope">
          <span v-if="scope.row.costeffective == 1">是</span>
          <span v-else>否</span>
        </template>
      </el-table-column>
      <el-table-column prop="rushsale" label="急售" min-width="80" sortable>
        <template slot-scope="scope">
          <span v-if="scope.row.rushsale == 1">是</span>
          <span v-else>否</span>
        </template>
      </el-table-column>
      <el-table-column prop="quasinewcar" label="准新车" min-width="80" sortable>
        <template slot-scope="scope">
          <span v-if="scope.row.quasinewcar == 1">是</span>
          <span v-else>否</span>
        </template>
      </el-table-column>
      <el-table-column prop="transitivecountry" label="可提全国" min-width="80" sortable>
        <template slot-scope="scope">
          <span v-if="scope.row.transitivecountry == 1">是</span>
          <span v-else>否</span>
        </template>
      </el-table-column>
      <el-table-column prop="type.name" label="类型" width="100" sortable>
      </el-table-column>
      <el-table-column prop="seller.username" label="营销人员" width="100" sortable>
      </el-table-column>
      <el-table-column prop="auditstate" label="审核状态" width="100" sortable>
        <template slot-scope="scope">
          <span v-if="scope.row.auditstate == 1">已审核</span>
          <span v-else>未审核</span>
        </template>
      </el-table-column>
			<el-table-column label="操作" min-width="240" fixed="right">
				<template scope="scope">
					<el-button size="small" @click="handleEdit(scope.$index, scope.row)">编辑</el-button>
					<el-button type="danger" size="small" @click="handleDel(scope.$index, scope.row)">删除</el-button>
					<el-button type="warning" size="small" @click="handleDel(scope.$index, scope.row)">资源维护</el-button>
				</template>
			</el-table-column>
		</el-table>

		<!--工具条 - 批量删除&分页条-->
		<el-col :span="24" class="toolbar">
			<el-button type="danger" @click="batchRemove" :disabled="this.sels.length===0">批量删除</el-button>
			<el-pagination
          layout="prev, pager, next"
          :current-page="query.currentPage"
          @current-change="handleCurrentChange"
          :page-size="query.pageSize"
          :total="pageInfo.total"
          style="float:right;">
			</el-pagination>
		</el-col>

		<!--新增界面-->
    <el-dialog title="新增/修改" :visible.sync="carFormVisible" :close-on-click-modal="false">
      <el-form :model="carForm" label-width="80px"  ref="carForm">
        <el-form-item label="标题">
          <el-input v-model="carForm.title" auto-complete="off"></el-input>
        </el-form-item>
        <el-form-item label="封面">
          <el-upload
              class="upload-demo"
              action="http://localhost:8090/commons/upload"
              :on-remove="coverHandleRemove"
              :on-success="coverHandleSuccess"
              :file-list="coverFileList"
              list-type="picture">
            <el-button size="small" type="primary">点击上传</el-button>
            <div slot="tip" class="el-upload__tip">只能上传jpg/png文件，且不超过500kb</div>
          </el-upload>
        </el-form-item>
        <el-form-item label="原价">
          <el-input v-model="carForm.saleprice" style="width:220px;" auto-complete="off"></el-input>
        </el-form-item>
        <el-form-item label="售价">
          <el-input v-model="carForm.costprice" style="width:220px;" auto-complete="off"></el-input>
        </el-form-item>
        <el-form-item label="是否新车">
          <el-radio-group v-model="carForm.isnew">
            <el-radio class="radio" :label="1">是</el-radio>
            <el-radio class="radio" :label="0">否</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="上牌时间">
          <el-date-picker
              value-format="yyyy-MM-dd"
              v-model="carForm.reigstertime"
              type="date"
              placeholder="请选择日期">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="里程数">
          <el-input v-model="carForm.mileage" style="width:220px;" type="number" auto-complete="off"></el-input>
        </el-form-item>
        <el-form-item label="超值">
          <el-radio-group v-model="carForm.costeffective">
            <el-radio class="radio" :label="1">是</el-radio>
            <el-radio class="radio" :label="0">否</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="急售">
          <el-radio-group v-model="carForm.rushsale">
            <el-radio class="radio" :label="1">是</el-radio>
            <el-radio class="radio" :label="0">否</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="准新车">
          <el-radio-group v-model="carForm.quasinewcar">
            <el-radio class="radio" :label="1">是</el-radio>
            <el-radio class="radio" :label="0">否</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="可迁全国">
          <el-radio-group v-model="carForm.transitivecountry">
            <el-radio class="radio" :label="1">是</el-radio>
            <el-radio class="radio" :label="0">否</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="车辆类型">
          <el-cascader v-model="carForm.type"
                       :options="carTypeTree"
                       :props="{
							   checkStrictly: true,
							   label:'name',
							   value:'id'
							}"
                       clearable></el-cascader>
        </el-form-item>
        <el-form-item label="基本信息">
           <!--<el-input type="textarea" v-model="carForm.carDetail.info"></el-input>-->
          <quill-editor v-model="carForm.carDetail.info" :options="quillOptions">
          </quill-editor>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click.native="carFormVisible = false">取消</el-button>
        <el-button type="primary" @click.native="save" :loading="addLoading">提交</el-button>
      </div>
    </el-dialog>
	</section>
</template>

<script>
import { quillEditor } from "vue-quill-editor"; // 在页面中引入富文本编辑框的组件
import 'quill/dist/quill.core.css';   // 引入样式
import 'quill/dist/quill.snow.css';
import 'quill/dist/quill.bubble.css';
import quillConfig from "../../common/js/quill-config.js"

	export default {
	  components:{
	    quillEditor   // 如果key和value的名字一样,可以省略 quillEditor:quillEditor
    },
		data() {
			return {
			  quillOptions:quillConfig,
				query:{
				  currentPage:1,  // 当前页
          pageSize:10,  // 每页显示条数
          keyword:""  // 关键字
        },
        pageInfo:{
				  total:0,
          rows:[]
        },
				listLoading: false,
				sels: [],//列表选中列
        
				carFormVisible: false,//新增修改界面是否显示
				carFormLoading: false,
				carFormRules: {
					name: [
						{ required: true, message: '请输入姓名', trigger: 'blur' }
					]
				},
        employees:[],
        carTypeTree:[],
        addLoading:false,
        coverFileList:[],
				//新增修改界面数据
        carForm: {
          id:null,
          title:"",
          cover:"",
          saleprice:0,
          costprice:0,
          isnew:0,
          reigstertime:"",
          mileage:null,
          costeffective:0,
          rushsale:0,
          quasinewcar:0,
          transitivecountry:0,
          type:{
            id:null,
            name:""
          },
          typeId:null,
          carDetail:{
            info:""
          }
        }
			}
		},
		methods: {
	    //车辆上架方法
      onSale(){
        //1.判断用户是否有选择的数据
        //2.把用户选择的数据 id传到后台 把id转换为数组[1,2,3,4,5]
        var ids = this.sels.map(item => item.id);
        this.$confirm('确认上架选中记录吗？', '提示', {
          type: 'warning'
        }).then(() => {
          this.listLoading = true;
          //let para = { ids: ids };
          this.$http.post("/car/onSale",ids).then(res=>{
            let {msg,code,success,data} = res.data;
            if (success){
              this.$message({
                message: '上架成功!',
                type: 'success'
              });
            }else {
              this.$message({
                message: '上架失败!'+msg,
                type: 'error'
              });
            }
            this.listLoading = false;
            this.getCars();
          })
        }).catch(() => {

        });

      },

		  // 成功回调
      coverHandleSuccess(response, file, fileList){
        this.carForm.cover = response.data;
      },
      //文件删除
      // file 当前要删除的文件  里面包含的response是后端上传接口返回的值
      // fileList 当前未被删除的所有的文件
      coverHandleRemove(file, fileList) {
        var filePath =file.response.resultObj;    // 当前要删除文件的url地址
        this.$http.delete("/fastdfs?path="+filePath)
            .then(res=>{
              if(res.data.success){
                this.$message({
                  message: '删除成功!',
                  type: 'success'
                });
                // 将shop.logo设置为null,避免将已经删除的图片的路径带到数据库
                this.carForm.cover = "";
              }else{
                this.$message({
                  message: '删除失败!',
                  type: 'error'
                });
              }
            })
      },

      search(){
        this.query.currentPage = 1;
        this.getCars();
      },
			handleCurrentChange(val) {  // val是当前页
				this.query.currentPage = val;
				this.getCars();
			},
			//获取部门列表
			getCars() {
				this.listLoading = true;  // 弹出加载框
				this.$http.post("/car",this.query)   // axios.defaults.baseURL+/car  http://localhost:80/api/car
                  .then(result => {
                    result = result.data; // result.data  {success:true,message:"",result:{total:1,rows:[]}}
                    console.log(result)
                    if(result.success){
                       this.pageInfo.total = result.data.total;
                       this.pageInfo.rows = result.data.list;
                       this.listLoading = false;  // 关闭加载框
                    }else{
                      this.$message({message: '查询分页失败!'+result.message, type: 'error'});
                    }
                  })
                  .catch(result => {
                    this.$message({message: "查询分页失败!", type: 'error'});
                  })
			},
			//删除
			handleDel: function (index, row) {
				this.$confirm('确认删除该记录吗?', '提示', {
					type: 'warning'
				}).then(() => {
					this.listLoading = true;
					this.$http.delete("/car/"+row.id)
                    .then(result => {
                       result = result.data;
                       if(result.success){
                         this.$message({message: '删除成功', type: 'success'});
                         // 刷新列表
                         this.query.currentPage = 1;
                         this.getCars();
                         this.listLoading = false;  // 关闭弹出框
                       }else{
                         this.$message({message: '删除失败!'+result.message, type: 'error'});
                       }
                    })
				}).catch(() => {
          this.$message({message: '已取消删除', type: 'info'});
				});
			},
      getEmployeeList(){
         this.$http.get("/employee")
              .then(result => {
                  result = result.data;
                  if(result.success){
                    this.employees = result.result;
                  }else{
                    this.$message({message: '获取员工列表失败!'+result.message, type: 'error'});
                  }
              }).catch(result => {
                this.$message({message: '获取员工列表失败!', type: 'error'});
              })
      },
			//显示编辑界面
			handleEdit: function (index, row) {
				this.carFormVisible = true;
				this.carForm = Object.assign({}, row);
				if(this.carForm.manager == null){
          this.carForm.manager = {id:null}
        }
        if(row.path != null){
           let arrays = row.path.split("/");    // ['', '1', '2', '20'] 根据/分割之后,第一个位置是一个空字符串,是不需要的,另外最后一个值是当前部门的id,而不是父部门的id,所以也不需要
           let ids = [];
           for(let i=1;i<arrays.length-1;i++){
             ids.push(parseInt(arrays[i]))    // 数组分割之后里面的值都是字符串,而部门树的id都是数字,他们匹配不上就不能回显,因此需要parseInt()转一下
           }
          this.carForm.parent = ids;
        }
        //this.carForm.parent = [1,2];
        // 获取所有的员工数据
        this.getEmployeeList();
        // 获取部门树
        this.getDeptTree();
			},
			//显示新增界面
			handleAdd: function () {
        // 弹出弹框
				this.carFormVisible = true;
				// 清空双向绑定的数据,避免将上一次填写的数据带到下一次
				this.carForm =  {
            id:null,
            title:"",
            cover:"",
            saleprice:0,
            costprice:0,
            isnew:0,
            brand:"",
            reigstertime:"",
            mileage:null,
            costeffective:0,
            rushsale:0,
            quasinewcar:0,
            transitivecountry:0,
            type:{
              id:null,
                  name:""
            },
            typeId:null,
                carDetail:{
              info:""
            }
        };
				// 调用查询车辆类型数,渲染下拉框
        this.getCarTypeTree();
			},
      getCarTypeTree(){
          this.$http.get('/carType/tree')
              .then(result => {
                  result = result.data;
                  if(result.success){
                      this.carTypeTree = result.data;
                  }
              })
      },
			//新增修改保存方法
			save: function () {
				this.$refs.carForm.validate((valid) => {
					if (valid) {
						this.$confirm('确认提交吗？', '提示', {}).then(() => {
							this.carFormLoading = true;
							let para = Object.assign({}, this.carForm);
							// 处理车辆类型,如果选择了车辆类型的话,它双向绑定的type是一个数组,将数组的最后一个位置的值赋值给typeId
              //debugger;
              if(para.type && para.type.length > 0){
                para.typeId = para.type[para.type.length-1];
                para.brand=para.type[0]
              }

							this.$http.put("/car",para)
                  .then(result => {
                      result = result.data;
                      if(result.success){
                        this.$message({message: '保存成功', type: 'success'});
                        this.carFormLoading = false;
                        this.query.currentPage = 1;
                        this.getCars();
                        this.carFormVisible = false;
                      }else{
                        this.$message({message: '保存失败!'+result.message, type: 'error'});
                      }
                  }).catch(result => {
                      this.$message({message: '保存失败!', type: 'error'});
                  })
						});
					}
				});
			},
			selsChange: function (sels) {
				this.sels = sels;
			},
			//批量删除
			batchRemove: function () {
				var ids = this.sels.map(item => item.id).toString();
				this.$confirm('确认删除选中记录吗？', '提示', {
					type: 'warning'
				}).then(() => {
					this.listLoading = true;
					//NProgress.start();
					let para = { ids: ids };
					batchRemoveUser(para).then((res) => {
						this.listLoading = false;
						//NProgress.done();
						this.$message({
							message: '删除成功',
							type: 'success'
						});
						this.getCars();
					});
				}).catch(() => {

				});
			}
		},
		mounted() { 
			this.getCars();  // 做分页查询
		}
	}

</script>

<style scoped>

</style>