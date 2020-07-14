<template>

	<el-row>
		<div>
			<h1>申请课程班级：</h1>
		</div>

		<el-form ref="courseForm" :model="courseForm"  label-width="80px" style="width: 50%;margin-top: 5%;">
			<el-form-item label="课程名称" prop="course_id">
				<!-- <el-input v-model="courseForm.course_name"></el-input> -->
				<el-select v-model="courseForm.course_id" filterable placeholder="请选择课程" @change="getCourseDetail">
					<el-option v-for="item in options" :key="item.id" :label="item.course_name" :value="item.id">
					</el-option>
				</el-select>
			</el-form-item>
			<el-form-item label="主讲教师">
				<el-input v-model="courseForm.teacher_name" disabled></el-input>
			</el-form-item>
			<el-form-item label="课程描述">
				<el-input type="textarea" v-model="courseForm.remark" disabled></el-input>
			</el-form-item>
			<el-form-item label="申请学生">
				<el-input v-model="courseForm.student_name" disabled></el-input>
			</el-form-item>
			<el-form-item>
				<el-button type="primary" @click="applyCourse">立即申请</el-button>
			</el-form-item>
		</el-form>
	</el-row>

</template>

<script>
	export default {
		data() {
			return {
				courseForm: {
					course_id: "",
					teacher_name: "",
					remark: "",
					student_name: ""
				},
				options: []
			};
		},
		methods: {
			getAllCourse() {
				this.courseForm.student_name = sessionStorage.getItem("student_name");
				this.$http
					.get("/course/findAllCourses", {
						params: {}
					}).then(res => {
						console.log(res.data);
						var courses = [];
						//Option = res.data.data;
						this.options = res.data.data;

					});
			},
			getCourseDetail(){
				this.$http
					.get("/course/findCoursesById", {
						params: {
							courseId:this.courseForm.course_id
						}
					}).then(res => {
						console.log(res.data);
						var course = res.data.data;
						this.courseForm.teacher_name = course.teacher_name;
						this.courseForm.remark = course.remark;
					});
			},
			applyCourse(){
				this.$http
					.get("/students/applyCourse", {
						params: {
							course_id:this.courseForm.course_id,
							student_name:this.courseForm.student_name
						}
					}).then(res => {
						console.log(res.data);
						if (res.data.msg == "success") {
							this.$message("申请已提交！");
							this.courseForm.course_id = "";
							this.courseForm.teacher_name = "";
							this.courseForm.remark = "";
						} 
						else if(res.data.msg ==="已申请过该课程"){
							this.$message("已申请过该课程！");
						}
						else {
							this.$message("申请失败！");
						}
					});
			}
		},
		mounted() {
			this.getAllCourse();
		}
	}
</script>

<style>
</style>
