<template>
	<el-row>
		<h2>{{paper.title}}</h2>
		<h1>发布人：{{paper.user_name}}</h1>
		<el-row>
			<el-col :span="12">
				<span>问卷描述：{{paper.remark}}</span>
				<div><span>开始时间：{{paper.begin_time}}</span></div>
				<div><span>结束时间：{{paper.finish_time}}</span></div>
			</el-col>
			<el-col :span="12">
				<el-button style="float: right;margin-top: 4%;" v-on:click="GOBack">返回</el-button>
			</el-col>
		</el-row>
		<el-divider></el-divider>

		<el-row v-for="(question,index) in questionlist" :key="index">
			<h3>{{index+1+'. '+question.question_content}}</h3>
			<h5>{{question.question_type}}</h5>
			<el-row>
				<el-col :span="24" v-for="(answer,i) in question.answerList" :key="i">
					<span>{{i+1+'. '+answer.question_option}}</span>
					<span v-if="question.question_type!='主观题'" style="float: right;">{{answer.answer_count+"人已选"}}</span>
				</el-col>
			</el-row>
			<el-row v-if="question.answerList[0]==null">
				<span>暂无回答</span>
			</el-row>
		</el-row>

	</el-row>
</template>

<script>
	export default {
		data() {
			return {
				paper: {
					id: null,
					title: "你好",
					remark: "非常好",
					user_name: "xxx",
					begin_time: "2020-03-23 11:00:00",
					finish_time: "2020-03-24 11:00:00",
					status: "调查中",
					paper_count: 0,
				},
				questionlist: [{
						question_content: "123",
						answerlist: [{}, {}]
					},
					{

					}
				]
			}
		},
		methods: {
			GOBack() {
				this.$router.push({
					path: "/main/myPaper"
				});
			},
			getPaperDetail() {
				var paper_id = window.location.search.substr(1);//从url里面拿到参数paper_id
				console.log(paper_id);
				this.$http
					.get("/papers/findPaperById", {
						params: {
							paper_id: paper_id
						}
					}).then(res => {
						console.log(res.data);
						this.paper = res.data.data;
						this.paper.begin_time = this.timestampToTime(this.paper.begin_time);
						this.paper.finish_time = this.timestampToTime(this.paper.finish_time);
					});
			},
			getQuestionlist() {
				var paper_id = window.location.search.substr(1);
				this.$http
					.get("/papers/findAllQuestionByPaper_id", {
						params: {
							paper_id: paper_id
						}
					}).then(res => {
						console.log(res.data);
						this.questionlist = res.data.data;
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
			this.getPaperDetail();
			this.getQuestionlist();
		}
	}
</script>

<style>
</style>
