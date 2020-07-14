<template>
	<el-container>
		<el-header>
			<span>网课作业系统——学生端</span>
			<el-dropdown trigger="hover" style="float: right;">
				<span class="el-dropdown-link">
					您好,{{currentStudent}}！
				</span>
				<el-dropdown-menu slot="dropdown">
					<el-dropdown-item @click.native="logout">退出登录</el-dropdown-item>
				</el-dropdown-menu>
			</el-dropdown>
		</el-header>
		<el-container>
			<el-aside width="200px">
				<el-menu background-color="lightsteelblue">
					<el-menu-item index="2" @click="create">
						<span slot="title" style="font-weight:bold">申请课程</span>
					</el-menu-item>
					<el-menu-item index="1" @click="handleToMine()">
						<span slot="title" style="font-weight:bold">我的课程</span>
					</el-menu-item>
				</el-menu>
			</el-aside>
			<el-main>
				<router-view></router-view>
			</el-main>
		</el-container>
	</el-container>
</template>

<script>
	export default {
		data() {
			return {
				currentStudent: "",
	
			}
		},
		methods: {
			getCurrentStudent() {
				this.currentStudent = sessionStorage.getItem("student_name");
			},
			logout(){
				this.$confirm("确认退出吗？","提示",{})
				.then(()=>{
					sessionStorage.removeItem("student_name");
					this.$router.push("/login");
				})
			},
			create(){
				this.$router.push("/main_student/applyCourse")
			},
			handleToMine(){
				this.$router.push("/main_student/myCourse_student")
			}
		},
		mounted() {
			this.getCurrentStudent();
		}
	}
</script>

<style lang="scss" scoped>
	.el-header {
		background-color: lightblue;
		color: #333;
		text-align: center;
		line-height: 60px;
		font-weight: bold;
		font-size: 20px;
	}

	.el-aside {
		background-color: lightsteelblue;
		color: #333;
		text-align: center;
		line-height: 200px;
		height: 670px;
	}

	.el-main {
		background-color: white;
		color: #333;
		height: 670px;
		
	}

	body>.el-container {
		margin-bottom: 40px;
	}

	.el-container:nth-child(5) .el-aside,
	.el-container:nth-child(6) .el-aside {
		line-height: 260px;
	}

	.el-container:nth-child(7) .el-aside {
		line-height: 320px;
	}
</style>

