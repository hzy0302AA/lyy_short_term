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
				<el-button style="float: right;margin-left: 1%;margin-top: 4%;" v-on:click="GOBack">返回</el-button>
				<el-button type="primary" style="float: right;margin-top: 4%;" v-on:click="onSubmit">提交</el-button>

			</el-col>
		</el-row>
		<el-divider></el-divider>

		<el-row v-for="(question,index) in questionlist" :key="index">
			<h3>{{index+1+'. '+question.question_content}}</h3>
			<h5>{{question.question_type}}</h5>
			<el-row>
				<el-radio-group v-model="answer_Ids[index]" v-if="question.question_type=='单选题'">
					<el-radio v-for="(answer,i) in question.answerList" :key="i" :label="answer.id">
						{{i+1+'. '+answer.question_option}}
					</el-radio>
				</el-radio-group> 
				<el-col v-if="question.question_type=='主观题'">
					<el-input type="textarea" style="width: 50%;" v-model="answer_text1[index]"
					@change="getanswertext(index)"
					></el-input>
					
					
				</el-col>
			</el-row>
		</el-row>

	</el-row>
</template>

<script>
	import axios from 'axios'
	export default {
		data() {
			return {
				answer_Ids: [],
				answer_text1:[],
				answer_text:[],
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
						id: "1",
						question_type: "单选题",
						question_content: "你好么",
						answerList: [{
								id: 1,
								question_option: "好"
							},
							{
								id: 2,
								question_option: "不好"
							}
						]
					},
					{
						id: "2",
						question_type: "单选题",
						question_content: "你好么",
						answerList: [{
								id: 1,
								question_option: "好"
							},
							{
								id: 2,
								question_option: "不好"
							}
						]
					},
					{
						id: "5",
						question_type: "主观题",
						question_content: "你哪里好",
						answerList: []
					},
				]
			}
		},
		methods: {
			getanswertext(index){
				console.log(index);
				let list = this.questionlist[index];
				if(list.question_type === "主观题")
				{
					this.answer_text.push({
						question_id:list.id,
						text:this.answer_text1[index]
					})
					}
			},
			getPaperDetail() {
				var paper_id = window.location.search.substr(1);
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
			GOBack() {
				this.$router.push({
					path: "/main/writePaper"
				});
			},
			onSubmit() {
				console.log(this.answer_Ids);
				console.log(this.answer_text);
				
				var answer_idList = JSON.stringify(this.answer_Ids);
				var answer_textList = JSON.stringify(this.answer_text);
				var params = new URLSearchParams();
				params.append("answer_idList",answer_idList);
				params.append("answer_textList",answer_textList);
				
				axios({
				    headers: {
				        'Content-Type': 'application/x-www-form-urlencoded'
				    },
				    method: 'post',
				    url: 'http://localhost:8081/papers/writePaper',
				    data: params
				}).then(res=>{
					console.log(res.data);
					if(res.data.msg=="success"){
						this.$message("感谢您的填写！");
						this.$router.push({
							path: "/main/writePaper"
						});
						
					}else{
						this.$message("提交失败");
					}
					
				})
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
