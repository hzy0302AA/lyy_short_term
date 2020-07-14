<template>
	<el-row>
		<h2>{{homework.homework_name}}</h2>
		<el-row>
			<el-col :span="12">
				<div><span>开始时间：{{homework.begin_time}}</span></div>
				<div><span>结束时间：{{homework.finish_time}}</span></div>
			</el-col>
			<el-col :span="12">
				<el-button style="float: right;margin-top: 4%;" v-on:click="GOBack">返回</el-button>
			</el-col>
		</el-row>
	<el-divider></el-divider>

	<el-table :data="submitList">
		<el-table-column type="index" label="序号" align="center" width="80"></el-table-column>
		<el-table-column property="student_id" align="center" label="学生编号" width="200"></el-table-column>
		<el-table-column property="student_name" align="center" label="学生姓名" width="200"></el-table-column>
		<el-table-column property="status" align="center" label="状态" width="200"></el-table-column>
		<el-table-column property="grade" align="center" label="分数" width="200"></el-table-column>
		<el-table-column property="submit_time" align="center" label="提交时间" width="200"></el-table-column>
		
		<el-table-column label="操作" min-width="200" align="center">
			<template slot-scope="scope">
				<el-button v-on:click="downloadSubmit(scope.row)" 
				:disabled="scope.row.status=='未提交'">下载作业</el-button>
				<el-button type="primary" :disabled="scope.row.status=='未提交'"
				 v-on:click="writeGrade(scope.row)">评分</el-button>
			</template>
		</el-table-column>
	</el-table>
	
	<el-dialog title="批改作业" :visible.sync="correct_flag" width="50%">
		<el-form ref="studentForm" :model="studentForm" label-width="80px" style="width: 80%;margin-top: 5%;">
			<el-form-item label="学生编号" prop="student_id">
				<el-input v-model="studentForm.student_id" disabled style="width: 80%;"></el-input>
			</el-form-item>
			<el-form-item label="学生姓名" prop="student_name">
				<el-input v-model="studentForm.student_name" disabled style="width: 80%;"></el-input>
			</el-form-item>
			<el-form-item label="分数" prop="grade">
				<el-input v-model="studentForm.grade" style="width: 80%;"></el-input>
			</el-form-item>
		</el-form>
		<span slot="footer" class="dialog-footer">
			<el-button @click="correct_flag = false">取 消</el-button>
			<el-button type="primary" @click="correct">提交</el-button>
		</span>
	</el-dialog>


	</el-row>
</template>

<script>
	export default {
		data() {
			return {
				homework:{
					homework_name: "aaa",
					begin_time:"",
					finish_time:"",
					course_id:""
				},
				submitList:[],
				correct_flag:false,
				studentForm:{
					submit_student_id:null,
					student_id:null,
					student_name:"",
					grade:null
				}
			}
		},
		methods:{
			downloadSubmit(row){
				console.log(row.submit_student_id);
				window.open("http://localhost:8081/homework/downloadSubmit?submit_student_id="
				+row.submit_student_id);
			},
			writeGrade(row){
				this.correct_flag = true;
				this.studentForm.submit_student_id = row.submit_student_id;
				this.studentForm.student_id = row.student_id;
				this.studentForm.student_name = row.student_name;
			},
			correct(){
				this.$http
					.get("/teachers/correctHomework", {
						params: {
							submit_student_id: this.studentForm.submit_student_id,
							grade: this.studentForm.grade
						}
					})
					.then(res => {
						if (res.data.msg == "success") {
							this.$message("评分成功！");
							this.correct_flag = false;
							this.getSubmitList();
						}else {
							this.$message("评分失败！")
						}
					});
			},
			GOBack(){
				this.$router.push("/main_teacher/courseDetail_teacher?"+this.homework.course_id);
			},
			getHomework(){
				var homework_id= window.location.search.substr(1); 
				this.$http
					.get("/homework/findHomeworkById", {
						params: {
							homework_teacher_id: homework_id
						}
					}).then(res => {
						console.log(res.data);
						this.homework.homework_name = res.data.data.homework_name;
						this.homework.begin_time = this.timestampToTime(res.data.data.begin_time);
						this.homework.finish_time = this.timestampToTime(res.data.data.finish_time);
						this.homework.course_id = res.data.data.course_id;
						this.getSubmitList();
					});
			},
			getSubmitList(){
				var homework_id= window.location.search.substr(1);
				this.$http
					.get("/homework/findAllHomework_submit", {
						params: {
							homework_id: homework_id,
							course_id:this.homework.course_id
						}
					}).then(res => {
						console.log(res.data);
						this.submitList = res.data.data;
						for (var i = 0; i < this.submitList.length; i++) {
							if(this.submitList[i].submit_time!==null){
								this.submitList[i].submit_time = this.timestampToTime(this.submitList[i].submit_time);
							}
							
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
			this.getHomework();
			//this.getSubmitList();
			//
			//this.homework.homework_name = homework_id
		}
	}
</script>

<style>
</style>
