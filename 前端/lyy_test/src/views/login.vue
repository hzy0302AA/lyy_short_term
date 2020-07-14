<template>
	<div class="back">
		<el-form :model="loginForm" ref="loginForm" label-position="left" label-width="0px" class="demo-ruleForm login-container"
		 style="height:320px;margin-top: 70px;" :rules="rule">
			<h3 class="title">网课作业系统</h3>
			<el-form-item prop="teacher_name">
				<el-input type="text" v-model="loginForm.teacher_name" auto-complete="off" placeholder="账号"></el-input>
			</el-form-item>
			<el-form-item prop="password">
				<el-input type="password" v-model="loginForm.password" auto-complete="off" placeholder="密码"></el-input>
			</el-form-item>
			<el-form-item>
				<el-radio-group v-model="loginForm.loginType" style="margin-left: 30%;">
					<el-radio :label="1">老师</el-radio>
					<el-radio :label="2">学生</el-radio>
				</el-radio-group>
			</el-form-item>
			<el-form-item style="width:100%;margin-left:3%">
				<el-row>
					<el-col :span="12">
						<el-button type="primary" style="width:90%;margin-top:8%;" @click.native.prevent="checkLogin_teacher" :loading="logining">登录</el-button>
					</el-col>
					<el-col :span="12">
						<el-button style="width:90%;margin-top:8%" v-on:click="register">注册</el-button>
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
				logining: false,
				loginForm: {
					teacher_name: "",
					password: "",
					loginType: 1
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
					}]
				}
			}
		},
		methods: {
			register() {
				this.$router.push({
					path: "/register"
				});
			},
			checkLogin_teacher() {
				this.$refs.loginForm.validate(valid => { //表单验证
					if (valid) {
						this.logining = true;

						if (this.loginForm.loginType === 1) {
							this.$http
								.get("/teachers/checkLogin_teacher", {
									params: {
										teacher_name: this.loginForm.teacher_name,
										password: this.loginForm.password,
									}
								})
								.then(res => {
									this.logining = false;
									if (res.data.msg == "教师不存在！") {
										this.$message("教师不存在！");
									} else if (res.data.msg == "登录成功") {
										sessionStorage.setItem("teacher_name", res.data.data);
										this.$router.push({
											path: "/main_teacher"
										});
									} else {
										this.$message("密码错误")
									}
									console.log(res.data.msg);
								});
						}else{
							this.$http
								.get("/students/checkLogin_student", {
									params: {
										student_name: this.loginForm.teacher_name,
										password: this.loginForm.password,
									}
								})
								.then(res => {
									this.logining = false;
									if (res.data.msg == "学生不存在！") {
										this.$message("学生不存在！");
									} else if (res.data.msg == "登录成功") {
										sessionStorage.setItem("student_name", res.data.data);
										this.$router.push({
											path: "/main_student"
										});
									} else {
										this.$message("密码错误")
									}
									console.log(res.data.msg);
								});
						}


					}
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
		margin-left: 60%;
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
