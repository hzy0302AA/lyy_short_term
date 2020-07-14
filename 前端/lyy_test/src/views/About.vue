<template>

	<el-table :data="tableData" style="width: 100%;">
		<el-table-column prop="date" label="日期" width="180"></el-table-column>
		<el-table-column prop="name" label="姓名" width="180"></el-table-column>
		<el-table-column prop="address" label="地址1" width="180"></el-table-column>
	</el-table>

</template>

<script>
	export default {
		data() {
			return {
				tableData: [{
						date: '2020-04-25',
						name: '王小虎',
						address: '杭州市拱墅区湖州街51号'
					},
					{
						date: '2020-04-25',
						name: '王小虎',
						address: '杭州市拱墅区湖州街51号'
					},
					{
						date: '2020-04-25',
						name: '王小虎',
						address: '杭州市拱墅区湖州街51号'
					}
				]
			}
		}
	}
</script>
