<template>
    <div>  
        <el-col :span="2">
            <el-button class="seBT" type="success" @click="dialogVisible=true">报修</el-button>
        </el-col>

        <el-dialog title="报修信息" :visible.sync="dialogVisible" width="30%" :before-close="handleClose"> 
            <el-form :label-position="'left'" label-width="100px">
        
            <el-form-item label="报修类型">
            <el-select v-model="typeId" placeholder="电设施维修">
                <el-option
                    v-for="item in opType"
                    :key="item.type"
                    :label="item.label"
                    :value="item.type">
                </el-option>
            </el-select>
            </el-form-item>

            <el-form-item label="详细信息">
            <el-input v-model="details"></el-input>
            </el-form-item>
        </el-form>
        <div>  
    <el-upload  
      class="avatar-uploader"  
      action="/api/user/common/upload"  
      :show-file-list="false"  
      :before-upload="beforeUpload"  
      :limit="1"  
      :on-exceed="handleExceed"  
      :file-list="fileList"  
      :auto-upload="false"  
    >  
    <!-- <img v-if="fileList.length === 0" src="你的默认图片地址" class="avatar">   -->
      <i class="el-icon-upload"></i>   
      <div class="el-upload__text">点击上传</div>  
      <div class="el-upload__tip" slot="tip">只能上传jpg/png文件，且不超过3MB</div>  
    </el-upload>  
    <!-- <el-button type="primary" @click="submitUpload">确认上传</el-button>   -->
  </div> 


            <span slot="footer" class="dialog-footer">
            <el-button @click="dialogVisible = false">取 消</el-button>
            <el-button type="primary" @click="submitUpload">确 定</el-button>
        </span>
    </el-dialog>

        <el-table
        class="table"
        :data="this.vo"
        style="width: 100%"
        :row-class-name="tableRowClassName"
        max-height="700px">
  
        <el-table-column prop="typeId" label="报修类型" width="400">
          <template slot-scope="scope">  
            <span v-if="scope.row.typeId === 1">电设施维修</span>  
            <span v-else-if="scope.row.typeId === 2">水设施维修</span>
            <span v-else-if="scope.row.typeId === 3">墙体路面维修</span> 
            <span v-else-if="scope.row.typeId === 4">电梯维修</span> 
            <span v-else-if="scope.row.typeId === 5">消防设施维修</span> 
            <span v-else>租借车位费</span>    
          </template> 
        </el-table-column>
  
        <el-table-column prop="image" label="照片" min-width="20%" >
          <!-- 图片的显示 -->
          <template   slot-scope="scope">            
            <img :src="scope.row.image"  min-width="70" height="70" />
          </template>         
        </el-table-column> 
  
        <el-table-column prop="details" label="描述" width="380"  :show-overflow-tooltip="true"></el-table-column>
      
        <el-table-column
          prop="status"
          label="状态"
          width="320">
          <template slot-scope="scope">  
            <span v-if="scope.row.status === 0">未解决</span>  
            <span v-else>已解决</span>  
          </template> 
        </el-table-column>
      </el-table>

      <el-pagination
          class="page"
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
          :page-sizes="[5, 10, 15, 20]"
          :page-size=this.pageSize
          :current-page=this.page
          layout="total, sizes, prev, pager, next"
          :total= this.total>
        </el-pagination>
      
    </div>
    
    </template>
    
    <script>
    import axios from 'axios'
      export default {
    
        created(){
          this.load();
        },
    
        methods: {    
          load(){
            this.userId = this.$store.state.user.id
            console.log('发请求前页数与大小', this.page, this.pageSize)
            var repairPageDTO = {
              page: this.page,
              pageSize: this.pageSize,
              userId: this.userId
            }
            axios({
                url: '/api/user/repair/page',
                method: 'get',
                params: repairPageDTO,
                headers: {
                token: this.$store.state.user.token
                }
                }).then(res => { // 成功回调函数再发请求
                if(res.data.code == 1){ // 成功
                    console.log("这是数据", res.data.data)
                    this.total = res.data.data.total;
                    this.vo = res.data.data.records;
                    console.log("这是f数据", this.total, this.vo)
                }else{
                    console.log(res.data.msg)
                }
            })
          },
    
          handleSizeChange(val) {
            this.pageSize = val;
            // console.log(`每页 ${val} 条`);
            console.log('每页', this.pageSize);
            this.load();
          },
    
          handleCurrentChange(val) {
            this.page = val;
            console.log('页数', this.page);
            // console.log(`当前页: ${val}`);
            this.load();
          },
    
          handleClose(done) {
            this.$confirm('确认关闭？')
              .then(_ => {
                done();
              })
              .catch(_ => {});
          },
        tableRowClassName({row, rowIndex}) {
            if (row.status === 0) {
            return 'warning-row';
            } else if (row.status === 1) {
            return 'success-row';
            }
            return '';
        },
        isEmptyStr(s) {
          if (s == null || s === '') {
            return true
          }
          return false
        },
    add(){
        if(this.isEmptyStr(this.imageUrl)){
            this.$message("图片未确认");
            return
        }
        console.log("开始插入数据库:{}",this.imageUrl)
            let repairUserDTO = {
                userId: this.userId,
                houseId: this.$store.state.house.id,
                typeId: this.typeId,
                image: this.imageUrl,
                details: this.details
            }
            axios({
                url: '/api/user/repair/add',
                method: 'post',
                data: repairUserDTO,
                headers: {
                    token: this.$store.state.user.token
                }
                }).then(res => { // 成功回调函数再发请求
                if(res.data.code == 1){ // 成功
                    this.$message("添加成功")
                    this.load();
                    this.dialogVisible = false
                }else{
                    this.$message(res.data.msg)
                }
            })
          },

        beforeUpload(file) {  
            console.log("sasaafasfasfa--------------------------------")
            const isJPG = file.type === 'image/jpeg';  
            const isPNG = file.type === 'image/png';  
            const isLt3M = file.size / 1024 / 1024 < 3; // 限制文件大小不超过3MB  
        
            if (!isJPG && !isPNG) {  
                this.$message.error('上传图片只能是 JPG/PNG 格式!');  
                return false;  
            }  
            if (!isLt3M) {  
                this.$message.error('上传图片大小不能超过 3MB!');  
                return false;  
            }  
            // this.fileList = fileList
            return true;  
        },  
  
    handleExceed(files, fileList) {  
        this.fileList = fileList
    //   this.$message.warning('当前限制选择 1 个文件，您已选择了 ' + files.length + ' 个文件，共选择了 ' + (files.length + fileList.length) + ' 个文件');  
        this.$message("选择成功")
    },  

    submitUpload() {  
      const formData = new FormData();  
      // 获取用户选择的文件，并添加到formData中  
      if (this.fileList.length > 0) {  
        formData.append('file', this.fileList[0].raw);  
        // 发送POST请求到后端接口  
        axios.post('/api/user/common/upload', formData, {  
          headers: {  
            'Content-Type': 'multipart/form-data',
             token: this.$store.state.user.token  
          }  
        }).then(response => {  
            console.log("这是返回数据", response.data)
            this.$message.success('上传成功');  
            this.fileList = []
            this.imageUrl = response.data.data; // imageUrl来存储图片地址  
            console.log("待上传地址", response.data.data)
            this.add();
        
        }).catch(error => {  
          this.$message.error('上传失败，请重试');  
        });  
      } else {  
        this.$message.error('请先选择文件');  
      }  
    }  
        },
        
  
        data() {
          return {
            page : 1,
            pageSize: 10,
            userId: 0,
            total: 0,
            dialogVisible: false,

            typeId: 1,
            image: '',
            details: '',

            fileList: [], // 上传的文件列表  
            uploadUrl: '/api/user/common/upload', // 后端上传接口地址  
            imageUrl: '',
    
            opType: [{ 
              type: 1,
              label: '电设施维修'
            },{
              type: 2,
              label: '水设施维修'
            },{
              type: 3,
              label: '墙体路面维修'
            },{
              type: 4,
              label: '电梯维修'
            },{
              type: 5,
              label: '消防设施维修'
            },{
              type: 6,
              label: '其它维修'
            }],
            vo: []
          }
        }
      }
      
    </script>
    
    <style>
      .search {
        display: flex;
        flex-wrap: wrap
      }
      .table{
        padding-top: 10px;
        margin-bottom: 10px;
        margin-top: 10px;
        min-height: 700px;
        max-height: 700px
      }
      .seTag{
        /*垂直居中 */
        vertical-align: middle;
        /*水平居中*/
        text-align: center;
      }
      .tag{
        font-size: 18px;
        padding-top: 18px;
        padding-left: 20px;
        padding-right: 5px;
      }
      .seBT{
        margin-top: 10px;
      }
      .el-table .cell {  
        text-align: center;  
      }
      .el-dialog{
        /* background: transparent;
        background-image: url('@/assets/login.png');
        background-size: 100% 100%; */
        background-color: #E9EEF3;
        border-radius: 20px;
      }
      .el-table .warning-row {
        background: oldlace;
      }
  
      .el-table .success-row {
       background: #f0f9eb;
      }
    </style>