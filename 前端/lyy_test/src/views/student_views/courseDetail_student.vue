<template>
	<el-row>
		<h2>{{course.course_name}}</h2>
		<h1>主讲教师：{{course.teacher_name}}</h1>
		<el-row>
			<el-col :span="12">
				<span>课程描述：{{course.remark}}</span>
			</el-col>
			<el-col :span="12">
				<el-button style="float: right;margin-top: 0%;" v-on:click="GOBack">返回</el-button>
			</el-col>
		</el-row>
		<el-divider></el-divider>
		
		<el-table :data="homeworkList">
			<el-table-column type="index" label="序号" align="center" width="80"></el-table-column>
			<el-table-column property="homework_name" align="center" label="作业题目" width="200"></el-table-column>
			<el-table-column property="begin_time" align="center" label="开始时间" width="200"></el-table-column>
			<el-table-column property="finish_time" align="center" label="结束时间" width="200"></el-table-column>
			<el-table-column property="status" align="center" label="状态" width="200"></el-table-column>
			<el-table-column property="grade" align="center" label="分数" width="200"></el-table-column>
			<el-table-column label="操作" min-width="200" align="center">
				<template slot-scope="scope">
					<el-button v-on:click="downloadHomework(scope.row)">下载附件</el-button>
					<el-button type="primary" :disabled="scope.row.status!=='未提交'"
					 v-on:click="submitHomework(scope.row)">提交作业</el-button>
				</template>
			</el-table-column>
		</el-table>
		
		<el-dialog title="提交作业" :visible.sync="submit_flag" width="50%">
			<el-form ref="homeworkForm" :model="homeworkForm" label-width="80px" style="width: 80%;margin-top: 5%;">
				<el-form-item label="作业题目" prop="homework_name">
					<el-input disabled v-model="homeworkForm.homework_name" style="width: 80%;"></el-input>
				</el-form-item>
				<el-form-item label="上传附件">
					<input type="file" ref="myfile">
				</el-form-item>
			</el-form>
			<span slot="footer" class="dialog-footer">
				<el-button @click="submit_flag = false">取 消</el-button>
				<el-button type="primary" @click="upload">新增</el-button>
			</span>
		</el-dialog>
		
		
	</el-row>
</template>

<script>
	export default{
		data(){
			return{
				course:{
					id:null,
					course_name:"C语言",
					teacher_name:"aaa",
					remark:"基础课"
				},
				homeworkList:[],
				submit_flag:false,
				homeworkForm:{
					homework_name:"",
					homework_teacher_id:null
				}
			};
		},
		methods:{
			downloadHomework(row){
				//var course_id = window.location.search.substr(1);
				console.log(row.homework_teacher_id);
				window.open("http://localhost:8081/homework/downloadHomework?id="+row.homework_teacher_id);
			},
			submitHomework(row){
				this.submit_flag = true;
				this.homeworkForm.homework_name = row.homework_name;
				this.homeworkForm.homework_teacher_id = row.homework_teacher_id;
			},
			upload(){
				var student_name = sessionStorage.getItem("student_name");
				
				let myfile = this.$refs.myfile;
				let files = myfile.files;
				let file = files[0];
				var formData = new FormData();
				formData.append("file", file);
				formData.append("homework_teacher_id",this.homeworkForm.homework_teacher_id);
				formData.append("student_name",student_name);
				this.$http({
					method: "post",
					url: "/students/submitHomework_student",
					data: formData
				}).then(res => {
					console.log(res.data);
					if (res.data.msg == "success") {
						this.$message("提交成功！");
						this.homeworkForm.homework_name = "";
						this.$refs.myfile.files = null;
						this.submit_flag = false;
						this.getAllHomework();
					} else {
						this.$message("提交失败！");
					}
				})
			},
			GOBack(){
				this.$router.push("/main_student/myCourse_student")
			},
			getCourseDetail() {
				var courseId = window.location.search.substr(1);//从url里面拿到参数paper_id
				console.log(courseId);
				this.$http
					.get("/course/findCoursesById", {
						params: {
							courseId: courseId
						}
					}).then(res => {
						console.log(res.data);
						this.course = res.data.data;
					});
			},
			getAllHomework(){
				var course_id = window.location.search.substr(1);
				var student_name = sessionStorage.getItem("student_name");
				this.$http
					.get("/homework/findAllHomework_stuList", {
						params: {
							course_id: course_id,
							student_name: student_name
						}
					}).then(res => {
						console.log(res.data);
						this.homeworkList = res.data.data;
						for (var i = 0; i < this.homeworkList.length; i++) {
							this.homeworkList[i].begin_time = this.timestampToTime(this.homeworkList[i].begin_time);
							this.homeworkList[i].finish_time = this.timestampToTime(this.homeworkList[i].finish_time);
						}
					});
			},
			timestampToTime(timestamp) {
				var date = new Date(timestamp); //时间戳为10位需*1000，时间戳为13位的话不需乘1000
			
				var Y = date.getFullYear() + '-';
				var M = (date.getMonth() + 1 < 10 ? '0' + (date.getMonth() + 1) : date.getMonth() + 1) + '-';
				var D = (date.getDate() < 10 ? '0' + date.getDate() : date.getDate()) + ' ';
				var h = (date.getHours() < 10 ? '0' + date.getHours() : date.getHours()) + ':';
				var m = (date.getMinutes() < 10 ? '0' + date.getMinutes() : date.getMinutes()) + ':';
				var s = (date.getSeconds() < 10 ? '0' + date.getSeconds() : date.getSeconds());
			
				var strDate = Y + M + D + h + m + s;
				return strDate;
			
			}
		},
		mounted(){
			this.getCourseDetail();
			this.getAllHomework();
		}
	}
</script>

<style>
</style>
