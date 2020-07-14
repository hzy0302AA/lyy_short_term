<template>
	<div class="back">
		<el-form :model="registerForm" ref="registerForm" label-position="left" label-width="0px" class="demo-ruleForm login-container"
		 style="height:380px;margin-top: 35px;" :rules="rule">
			<h3 class="title">注册</h3>
			<el-form-item prop="teacher_name">
				<el-input type="text" v-model="registerForm.teacher_name" auto-complete="off" placeholder="请输入用户名"></el-input>
			</el-form-item>
			<el-form-item prop="password">
				<el-input type="password" v-model="registerForm.password" auto-complete="off" placeholder="请输入密码"></el-input>
			</el-form-item>
			<el-form-item prop="check_password">
				<el-input type="password" v-model="registerForm.check_password" auto-complete="off" placeholder="请再次输入密码"></el-input>
			</el-form-item>
			<el-form-item>
				<el-radio-group v-model="registerForm.registerType" style="margin-left: 30%;">
					<el-radio :label="1">老师</el-radio>
					<el-radio :label="2">学生</el-radio>
				</el-radio-group>
			</el-form-item>
			<el-form-item style="width:100%;margin-left:3%">
				<el-row>
					<el-col :span="12">
						<el-button type="primary" style="width:90%;margin-top:8%" v-on:click="register">注册</el-button>
					</el-col>
					<el-col :span="12">
						<el-button style="width:90%;margin-top:8%" v-on:click="comeback">返回</el-button>
					</el-col>
				</el-row>

			</el-form-item>
		</el-form>
	</div>
</template>

<script>
	export default {
		data() {
			return {
				registerForm: {
					teacher_name: "",
					password: "",
					check_password: "",
					registerType: 1
				},
				rule: {
					teacher_name: [{
						required: true,
						message: "请输入用户名",
						trigger: "blur"
					}],
					password: [{
						required: true,
						message: "请输入密码",
						trigger: "blur"
					}],
					check_password: [{
						required: true,
						message: "请再次输入密码",
						trigger: "blur"
					}]
				}
			}
		},
		methods: {
			register() {
				this.$refs.registerForm.validate(valid => { //表单验证
					if (valid) {
						if (this.registerForm.password != this.registerForm.check_password) {
							this.$message("两次密码不一样！")
						} else {

							if (this.registerForm.registerType === 1) {
								var teacher = new URLSearchParams();
								teacher.append("teacher_name", this.registerForm.teacher_name);
								teacher.append("password", this.registerForm.password);
								this.$http({
									method: "post",
									url: "/teachers/createTeacher",
									data: teacher
								}).then(res => {
									console.log(res.data);
									if (res.data.msg == "success") {
										this.$message("注册成功，请登录！");
										this.$router.push({
											path: "/login"
										});
									} else {
										this.$message("同名教师已存在！");
									}
								})
							} else {
								var student = new URLSearchParams();
								student.append("student_name",this.registerForm.teacher_name);
								student.append("password",this.registerForm.password);
								this.$http({
									method: "post",
									url: "/students/createStudent",
									data: student
								}).then(res => {
									console.log(res.data);
									if (res.data.msg == "success") {
										this.$message("注册成功，请登录！");
										this.$router.push({
											path: "/login"
										});
									} else {
										this.$message("同名学生已存在！");
									}
								})
								
								
								
							}

						}
					}
				});
			},
			comeback() {
				this.$router.push({
					path: "/login"
				});
			}
		}
	}
</script>

<style lang="scss" scoped>
	.back {
		width: 100%;
		height: 500px;
		margin-top: 100px;
		background-image: url(../../pic/a.jpeg);
	}

	.login-container {
		position: absolute;
		-webkit-border-radius: 5px;
		border-radius: 5px;
		-moz-border-radius: 5px;
		background-clip: padding-box;
		margin-left: 35%;
		margin-top: 50px;
		width: 350px;
		padding: 35px 35px 15px 35px;
		background: #fff;
		border: 1px solid #eaeaea;
		box-shadow: 0 0 25px #cac6c6;

		.title {
			margin: 0px auto 40px auto;
			text-align: center;
			color: #505458;
		}
	}
</style>
