<template>
	<el-row>
		<el-col :span="24" class="toolbar"><span style="font-weight: bold;">我的课程</span></el-col>
		<el-table :data="courselist.slice((currentPage-1)*10,currentPage*10)" stripe style="width: 100%" border>
			<el-table-column type="index" label="序号" align="center" width="100"></el-table-column>
			<el-table-column prop="id" label="课程编号" width="95" align="center">
			</el-table-column>
			<el-table-column prop="course_name" label="课程名称" width="120" align="center">
			</el-table-column>
			<el-table-column prop="teacher_name" label="主讲教师" width="300" align="center">
			</el-table-column>
			<el-table-column prop="remark" label="课程描述" min-width="100" align="center">
			</el-table-column>
			<el-table-column label="操作" width="100" align="center">
				<template slot-scope="scope">
					<el-button type="text" v-on:click="courseDetail(scope.row.id)">进入课程</el-button>
				</template>
			</el-table-column>
		</el-table>
		<el-col :span="24" class="toolbar">
			<el-pagination layout="prev, pager, next" @current-change="handleCurrentChange" :current-page="currentPage"
			 :page-size="10" :total="totalCount" style="float:right;"></el-pagination>
		</el-col>
	</el-row>
</template>

<script>
	export default {
		data() {
			return {
				tableData: [],
				courselist: [],
				totalCount: 1,
				currentPage: 1,
			}
		},
		methods: {
			handleCurrentChange(val) {
				// 改变默认的页数
				this.currentPage = val;
			},
	
			getAllMyCourses() {
				var student_name = sessionStorage.getItem("student_name");
				console.log(student_name);
				this.$http
					.get("/students/findCoursesByStudent_name", {
						params: {
							student_name: student_name
						}
					}).then(res => {
						console.log(res.data);
						var courses = [];
						courses = res.data.data;
						this.courselist = courses;
	
					});
			},
			courseDetail(courseId){
				this.$router.push({
					path: "/main_student/courseDetail_student?" + courseId
				});
			}
		
		},
		mounted() {
			this.getAllMyCourses();
		}
	}
</script>

<style>
</style>
