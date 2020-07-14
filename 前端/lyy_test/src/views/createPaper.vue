<template>
	<el-row>
		<el-col>
			<span style="font-weight: bold;">创建问卷</span>
			<el-divider></el-divider>
			<el-form :model="createForm" ref="createForm" label-width="100px" style="margin-top: 0%;">
				<el-form-item label="问卷名称:" style="width: 50%;" prop="title">
					<el-input v-model="createForm.title"></el-input>
				</el-form-item>
				<el-form-item label="问卷描述:" style="width: 50%;" prop="remark">
					<el-input type="textarea" v-model="createForm.remark"></el-input>
				</el-form-item>
				<el-form-item label="起止时间:" prop="time">
					<el-date-picker v-model="createForm.time" type="datetimerange" align="right" start-placeholder="开始"
					 end-placeholder="结束" :default-time="['12:00:00', '12:00:00']">
					</el-date-picker>
				</el-form-item>
				<el-divider></el-divider>
				<span style="font-weight: bold;">问题详情</span>
				<span style="float: right;">
					<el-button type="text" v-on:click="addQuestion">+添加问题</el-button>
				</span>
				<!-- 循环 -->
				<el-row v-for="(question,index) in createForm.questionlist" :key="index" style="width: 60%;margin-top: 2%;">
					<el-row>
						<el-col :span="22">
							<el-form-item :label="'问题内容'+(index+1)+':'" :prop="'questionlist.'+index+'.question_content'" :rules="{required:true,message:'请输入问题内容',trigger:'blur'}">
								<el-input type="textarea" v-model="question.question_content"></el-input>
							</el-form-item>
						</el-col>
						<el-col :span="2" v-if="index!=0">
							<el-button type="text" style="float: right;color: red;" v-on:click="deleteQuestion(question)">删除</el-button>
						</el-col>
					</el-row>
					<el-form-item label="问题类型:" :prop="'questionlist.'+index+'.question_type'">
						<el-radio-group v-model="question.question_type">
							<el-radio :label="1">单选题</el-radio>
							<el-radio :label="2">主观题</el-radio>
						</el-radio-group>
					</el-form-item>

					<div v-if="question.question_type!=2">
						<el-row v-for="(option,i) in question.question_option" :key="i">
							<el-col :span="20">
								<el-form-item :label="'选项'+(i+1)+':'">
									<el-input type="textarea" v-model="option.value"></el-input>
								</el-form-item>
							</el-col>
							<el-col :span="2" v-if="i!=0">
								<el-button type="text" style="float: right;color: red;" v-on:click="deleteOption(index,option)">删除</el-button>
							</el-col>
							<el-col :span="2"></el-col>
						</el-row>
						<el-row>
							<span style="margin-left: 6%;">
								<el-button type="text" v-on:click="addOption(index)">+添加选项</el-button>
							</span>
						</el-row>
					</div>
				</el-row>
				<el-form-item>
					<el-button type="primary" @click="onSubmit" style="margin-top: 1%;margin-left: 15%;">立即发布</el-button>
				</el-form-item>
			</el-form>
		</el-col>
	</el-row>
</template>

<script>
	import axios from 'axios'
	export default {
		data() {
			return {
				createForm: {
					title: "",
					remark: "",
					time: "",
					questionlist: [{
						question_type: 1,
						question_content: "",
						question_option: [{
							value: ""
						}]
					}]
				}
			}
		},
		methods: {
			addOption(index) {
				this.createForm.questionlist[index].question_option.push({
					//value: ""
				})
			},
			deleteOption(i, item) {
				var index = this.createForm.questionlist[i].question_option.indexOf(item);
				if (index !== -1) {
					this.createForm.questionlist[i].question_option.splice(index, 1);
				}
			},
			addQuestion() {
				this.createForm.questionlist.push({
					question_type: 1,
					question_content: "",
					question_option: [{
						value: ""
					}]
				})
			},
			deleteQuestion(item) {
				var index = this.createForm.questionlist.indexOf(item);
				if (index != -1) {
					this.createForm.questionlist.splice(index, 1);
				}
			},
			onSubmit() {
				var user_name = sessionStorage.getItem("user_name");
				console.log(this.createForm.time[1]);
				var start = this.timestampToTime(this.createForm.time[0]);
				var finish = this.timestampToTime(this.createForm.time[1]);
				console.log(start);

				console.log(this.createForm);

				var questionlists = JSON.stringify(this.createForm.questionlist); //数组转JSON格式，后端String类型接收 

				var params = new URLSearchParams();
				params.append("user_name", user_name);
				params.append("title", this.createForm.title);
				params.append("remark", this.createForm.remark);
				params.append("start_time", start);
				params.append("finish_time", finish);
				params.append("questionlists", questionlists);

				axios({
					headers: {
						'Content-Type': 'application/x-www-form-urlencoded'
					},
					method: 'post',
					url: 'http://localhost:8081/papers/createPaper',
					data: params
				}).then(res => {
					console.log(res.data);
					if (res.data.msg == "success") {
						this.$message("发布成功！");
						this.createForm.title = "";
						this.createForm.remark = "";
						this.createForm.time = "";

						this.createForm.questionlist = [{
							question_type: 1,
							question_content: "",
							question_option: []
						}]
					} else {
						this.$message("发布失败！");
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
		}
	}
</script>

<style>
</style>
