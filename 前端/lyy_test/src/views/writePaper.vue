<template>
	<el-row>
		<el-col :span="24" class="toolbar"><span style="font-weight: bold;">填写问卷</span></el-col>
		<el-table :data="paperlist.slice((currentPage-1)*10,currentPage*10)" stripe style="width: 100%" border>
			<el-table-column prop="id" label="问卷编号" width="95" align="center">
			</el-table-column>
			<el-table-column prop="title" label="问卷名称" width="120" align="center">
			</el-table-column>
			<el-table-column prop="remark" label="描述" width="300" align="center">
			</el-table-column>
			<el-table-column prop="status" label="状态" width="100" align="center">
			</el-table-column>
			<el-table-column prop="begin_time" label="开始时间" width="180" align="center">
			</el-table-column>
			<el-table-column prop="finish_time" label="结束时间" width="180" align="center">
			</el-table-column>
			<!-- <el-table-column prop="paper_count" label="数量" width="100" align="center">
			</el-table-column> -->
			<el-table-column label="操作" min-width="100" align="center">
				<template slot-scope="scope">
					<el-button type="text" v-on:click="writePaper(scope.row.id)">填写</el-button>
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
				paperlist: [],
				totalCount: 1,
				currentPage: 1,
			}
		},
		methods: {
			handleCurrentChange(val) {
				// 改变默认的页数
				this.currentPage = val;
			},
			getAllWritePaper() {
				this.$http
					.get("/papers/findAllWritePaper", {
						params: {}
					}).then(res => {
						var papers = [];
						papers = res.data.data;
						for (var i = 0; i < papers.length; i++) {
							papers[i].begin_time = this.timestampToTime(papers[i].begin_time);
							papers[i].finish_time = this.timestampToTime(papers[i].finish_time);
						}
						this.paperlist = papers;
						this.totalCount = papers.length;
					});
			},
			writePaper(paperid) {
				this.$router.push({
					path: "/main/writePaperDetail?" + paperid
				});
			},
			timestampToTime(timestamp) { //转换时间格式为yy-mm-dd hh:mm:ss
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
			this.getAllWritePaper();
		}
	}
</script>

<style>
</style>
