<template>
	<el-row>
		<div>
			<h1>创建课程班级：</h1>
		</div>

		<el-form ref="courseForm" :model="courseForm" :rules="rule" label-width="80px" style="width: 50%;margin-top: 5%;">
			<el-form-item label="课程名称" prop="course_name">
				<el-input v-model="courseForm.course_name"></el-input>
			</el-form-item>
			<el-form-item label="主讲教师">
				<el-input v-model="courseForm.teacher_name" disabled></el-input>
			</el-form-item>
			<el-form-item label="课程描述" prop="remark">
				<el-input type="textarea" v-model="courseForm.remark"></el-input>
			</el-form-item>
			<el-form-item>
				<el-button type="primary" @click="createCourse">立即创建</el-button>
				<el-button>取消</el-button>
			</el-form-item>
		</el-form>
	</el-row>
</template>

<script>
	export default {
		data() {
			return {
				courseForm: {
					course_name: "",
					teacher_name: "",
					remark: ""

				},
				rule: {
					course_name: [{
						required: true,
						message: "请输入课程名称",
						trigger: "blur"
					}],
					remark: [{
						required: true,
						message: "请输入课程描述",
						trigger: "blur"
					}]
				}
			}
		},
		methods: {
			getCurrentTeacher() {
				this.courseForm.teacher_name = sessionStorage.getItem("teacher_name");
			},
			createCourse() {
				this.$refs.courseForm.validate(valid => { //表单验证
					if (valid) {

						var course = new URLSearchParams();
						course.append("course_name", this.courseForm.course_name);
						course.append("teacher_name", this.courseForm.teacher_name);
						course.append("remark", this.courseForm.remark);
						this.$http({
							method: "post",
							url: "/course/createCourse",
							data: course
						}).then(res => {
							console.log(res.data);
							if (res.data.msg == "success") {
								this.$message("创建成功！");
								this.courseForm.course_name = "";
								this.courseForm.remark = "";
							} else {
								this.$message("同名课程已存在！");
							}
						})


					}
				});
			},
		},
		mounted() {
			this.getCurrentTeacher();
		}

	}
</script>

<style>
</style>
