<template>
	<el-row>
		<h2>{{course.course_name}}</h2>
		<h1>主讲教师：{{course.teacher_name}}</h1>
		<el-row>
			<el-col :span="12">
				<span>课程描述：{{course.remark}}</span>
			</el-col>
			<el-col :span="12">
				<el-button type="primary" style="float: right;margin-top: 0%;margin-left: 3%;" v-on:click="openApplyList">查看申请</el-button>
				<el-button type="primary" style="float: right;margin-top: 0%;margin-left: 3%;" v-on:click="createHomework">新增作业</el-button>
				<el-button style="float: right;margin-top: 0%;" v-on:click="GOBack">返回</el-button>
			</el-col>
		</el-row>
		<el-divider></el-divider>
		
		<el-table :data="homeworkList">
			<el-table-column type="index" label="序号" align="center" width="100"></el-table-column>
			<el-table-column property="homework_name" align="center" label="作业题目" width="350"></el-table-column>
			<el-table-column property="begin_time" align="center" label="开始时间" width="250"></el-table-column>
			<el-table-column property="finish_time" align="center" label="结束时间" width="250"></el-table-column>
			<el-table-column label="操作" min-width="200" align="center">
				<template slot-scope="scope">
					<el-button type="primary" v-on:click="downloadHomework(scope.row)">下载附件</el-button>
					<el-button type="warning" v-on:click="correctHomework(scope.row)">批改作业</el-button>
				</template>
			</el-table-column>
		</el-table>
		
		

		<el-dialog title="学生申请列表" :visible.sync="apply_flag" width="70%">
			<el-table :data="applyList">
				<el-table-column type="index" label="序号" align="center" width="100"></el-table-column>
				<el-table-column property="stu_course_id" align="center" label="申请编号" width="150"></el-table-column>
				<el-table-column property="student_id" align="center" label="学生编号" width="150"></el-table-column>
				<el-table-column property="student_name" align="center" label="学生姓名" width="200"></el-table-column>
				<el-table-column property="status" align="center" label="状态" min-width="150"></el-table-column>
				<el-table-column label="操作" width="200" align="center">
					<template slot-scope="scope">
						<el-button type="primary" v-on:click="agreeApply(scope.row.stu_course_id)">同意</el-button>
						<el-button type="warning" v-on:click="refuseApply(scope.row.stu_course_id)">拒绝</el-button>
					</template>
				</el-table-column>
			</el-table>
			<span slot="footer" class="dialog-footer">
				<el-button @click="apply_flag = false">取 消</el-button>
				<el-button type="primary" @click="apply_flag = false">确 定</el-button>
			</span>
		</el-dialog>

		<el-dialog title="新增作业" :visible.sync="dialogVisible" width="50%">
			<el-form ref="homeworkForm" :model="homeworkForm" label-width="80px" style="width: 80%;margin-top: 5%;">
				<el-form-item label="作业题目" prop="homework_name">
					<el-input v-model="homeworkForm.homework_name" style="width: 80%;"></el-input>
				</el-form-item>
				<el-form-item label="起止时间" prop="time">
					<el-date-picker v-model="homeworkForm.time" type="datetimerange" align="right" start-placeholder="开始"
					 end-placeholder="结束" :default-time="['12:00:00', '12:00:00']">
					</el-date-picker>
				</el-form-item>
				<el-form-item label="上传附件">
					<input type="file" ref="myfile">
				</el-form-item>
			</el-form>
			<span slot="footer" class="dialog-footer">
				<el-button @click="dialogVisible = false">取 消</el-button>
				<el-button type="primary" @click="uploadHomework">新增</el-button>
			</span>
		</el-dialog>



	</el-row>
</template>

<script>
	export default {
		data() {
			return {
				course: {
					id: null,
					course_name: "C语言",
					teacher_name: "aaa",
					remark: "基础课"
				},
				dialogVisible: false,
				apply_flag: false,
				applyList: [],
				homeworkList:[],
				homeworkForm: {
					homework_name: "",
					time: ""

				}
			};
		},
		methods: {
			downloadHomework(row){
				//var course_id = window.location.search.substr(1);
				console.log(row.id);
				window.open("http://localhost:8081/homework/downloadHomework?id="+row.id);
			},
			correctHomework(row){
				var course_id = window.location.search.substr(1);
				this.$router.push("/main_teacher/correctHomework?"+row.id);
			},
			getAllHomework(){
				var course_id = window.location.search.substr(1);
				this.$http
					.get("/homework/findAllHomeworkByCourse_id", {
						params: {
							course_id: course_id
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
			GOBack() {
				this.$router.push("/main_teacher/myCourse_teacher")
			},
			getCourseDetail() {
				var courseId = window.location.search.substr(1); 
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
			createHomework() {
				this.dialogVisible = true;
			},
			openApplyList() {
				this.apply_flag = true;
				var course_id = window.location.search.substr(1);
				this.$http
					.get("/teachers/findApplyListByCourse_id", {
						params: {
							course_id: course_id
						}
					}).then(res => {
						console.log(res.data);
						this.applyList = res.data.data;
					});
			},
			agreeApply(stu_course_id) {
				this.$confirm("确定操作信息么？", "提示", {
					cancelButtonText: "取消",
					confirmButtonText: "确定",
					type: "warning"
				}).then(() => {
					this.$http
						.get("/teachers/updateApplyCourse", {
							params: {
								stu_course_id: stu_course_id,
								status: "已通过"
							}
						}).then(res => {
							console.log(res.data);
							if (res.data.msg == "success") {
								this.$message("已同意！");
								this.apply_flag = false;
							} else {
								this.$message("操作失败！")
							}
						});
				}).catch(() => {
					this.apply_flag = false;
				});
			},
			refuseApply(stu_course_id) {
				this.$confirm("确定操作信息么？", "提示", {
					cancelButtonText: "取消",
					confirmButtonText: "确定",
					type: "warning"
				}).then(() => {
					this.$http
						.get("/teachers/updateApplyCourse", {
							params: {
								stu_course_id: stu_course_id,
								status: "已拒绝"
							}
						}).then(res => {
							console.log(res.data);
							if (res.data.msg == "success") {
								this.$message("已拒绝！");
								this.apply_flag = false;
							} else {
								this.$message("操作失败！")
							}
						});
				}).catch(() => {
					this.apply_flag = false;
				});
			},
			uploadHomework(){
				var course_id = window.location.search.substr(1);
				
				console.log(this.homeworkForm.time);
				var begin = this.timestampToTime(this.homeworkForm.time[0]);
				var finish = this.timestampToTime(this.homeworkForm.time[1]);
				console.log(begin);
				
				let myfile = this.$refs.myfile;
				let files = myfile.files;
				let file = files[0];
				var formData = new FormData();
				formData.append("file", file);
				formData.append("homework_name",this.homeworkForm.homework_name);
				formData.append("begin_time",begin);
				formData.append("finish_time",finish);
				formData.append("course_id",course_id);
				this.$http({
					method: "post",
					url: "/teachers/addHomework",
					data: formData
				}).then(res => {
					console.log(res.data);
					if (res.data.msg == "success") {
						this.$message("新增成功！");
						this.homeworkForm.homework_name = "";
						this.homeworkForm.time = "";
						this.$refs.myfile.files = null;
						this.dialogVisible = false;
						this.getAllHomework();
					} else {
						this.$message("新增失败！");
					}
				})
				
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
		mounted() {
			this.getCourseDetail();
			this.getAllHomework();
		}
	}
</script>

<style>
</style>
