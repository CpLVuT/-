<template>
  <div>
    <h1>横向课题</h1>
    <div class="buttons-container">
      <el-button type="primary" @click="showCreateForm = true">添加横向课题</el-button>
      <el-button type="success" @click="exportToExcel">导出为Excel</el-button>
    </div>
    <el-dialog v-model="showCreateForm" title="添加横向课题" width="80%" >
      <el-form @submit.prevent="createHorizontalProject" label-width="120px" class="demo-ruleForm">
        <el-form-item label="项目名称 :">
          <el-input v-model="newProject.projectName" placeholder="请输入项目名称"></el-input>
        </el-form-item>
        <el-form-item label="合同代码 :">
          <el-input v-model="newProject.contractCode" placeholder="请输入合同代码"></el-input>
        </el-form-item>
        <el-form-item label="总金额 :">
          <el-input v-model="newProject.totalAmount" type="number" placeholder="请输入总金额"></el-input>
        </el-form-item>
        <el-form-item label="甲方名称 :">
          <el-input v-model="newProject.partyAName" placeholder="请输入甲方名称"></el-input>
        </el-form-item>
        <el-form-item label="项目负责人 :">
          <el-input v-model="newProject.projectLeader" placeholder="请输入项目负责人"></el-input>
        </el-form-item>
        <el-form-item label="执行负责人 :">
          <el-input v-model="newProject.executiveLeader" placeholder="请输入执行负责人"></el-input>
        </el-form-item>
        <el-form-item label="项目周期">
          <el-input v-model="newProject.duration" placeholder="请输入项目周期"></el-input>
        </el-form-item>
        <el-form-item label="经费记录 :">
          <el-input v-model="newProject.fundingRecord" placeholder="请输入经费记录"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="createHorizontalProject">提交</el-button>
          <el-button @click="showCreateForm = false">取消</el-button>
        </el-form-item>
      </el-form>
    </el-dialog>

    <el-table :data="horizontalProjects" style="width: 100%" v-if="horizontalProjects.length > 0">
      <el-table-column prop="projectName" label="项目名称"></el-table-column>
      <el-table-column prop="contractCode" label="合同代码"></el-table-column>
      <el-table-column prop="totalAmount" label="总金额"></el-table-column>
      <el-table-column prop="partyAName" label="甲方名称"></el-table-column>
      <el-table-column prop="projectLeader" label="项目负责人"></el-table-column>
      <el-table-column prop="executiveLeader" label="执行负责人"></el-table-column>
      <el-table-column prop="duration" label="项目周期"></el-table-column>
      <el-table-column prop="fundingRecord" label="经费记录"></el-table-column>
      <el-table-column label="操作">
        <template v-slot="scope">
          <el-button @click="editProject(scope.row)" type="text" circle size=small>编辑
            <el-icon name="edit"></el-icon>
          </el-button>
          <el-button @click="deleteProject(scope.row.id)" type="text" circle size=small>删除
            <el-icon name="delete"></el-icon>
          </el-button>
        </template>
      </el-table-column>
    </el-table>

    <el-dialog v-model:visible="showEditForm" title="编辑横向课题" width="80%" >
      <el-form @submite.prevent="updateHorizontalProject" label-width="120px" class="demo-ruleForm">
        <el-form-item label="项目名称 :">
          <el-input v-model="currentProject.projectName" placeholder="请输入项目名称"></el-input>
        </el-form-item>
        <el-form-item label="合同代码 :">
          <el-input v-model="currentProject.contractCode" placeholder="请输入合同代码"></el-input>
        </el-form-item>
        <el-form-item label="总金额 :">
          <el-input v-model="currentProject.totalAmount" type="number" placeholder="请输入总金额"></el-input>
        </el-form-item>
        <el-form-item label="甲方名称 :">
          <el-input v-model="currentProject.partyAName" placeholder="请输入甲方名称"></el-input>
        </el-form-item>
        <el-form-item label="项目负责人 :">
          <el-input v-model="currentProject.projectLeader" placeholder="请输入项目负责人"></el-input>
        </el-form-item>
        <el-form-item label="执行负责人 :">
          <el-input v-model="currentProject.executiveLeader" placeholder="请输入执行负责人"></el-input>
        </el-form-item>
        <el-form-item label="项目周期 :">
          <el-input v-model="currentProject.duration" placeholder="请输入项目周期"></el-input>
        </el-form-item>
        <el-form-item label="经费记录 :">
          <el-input v-model="currentProject.fundingRecord" placeholder="请输入经费记录"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="updateHorizontalProject">提交</el-button>
          <el-button @click="showEditForm = false">取消</el-button>
        </el-form-item>
      </el-form>
    </el-dialog>
  </div>
</template>

<script>

  import axios from '../api/axios'; // 根据实际情况导入axios
  import * as XLSX from 'xlsx'; // 根据实际情况导入xlsx


export default {
  data() {
    return {
      horizontalProjects: [],
      showCreateForm: false,
      showEditForm: false,
      newProject: {
        projectName: '',
        contractCode: '',
        totalAmount: '',
        partyAName: '',
        projectLeader: '',
        executiveLeader: '',
        duration: '',
        fundingRecord: ''
      },
      currentProject: {}
    };
  },
  methods: {
    async fetchHorizontalProjects() {
      const response = await axios.get('/horizontal-projects/approved'); // 更新为审核通过的数据接口
      this.horizontalProjects = response.data;
    },
    async createHorizontalProject() {
      await axios.post('/horizontal-projects', this.newProject); // 提交新横向课题到后端
      await this.fetchHorizontalProjects(); // 刷新列表数据
      this.showCreateForm = false; // 关闭添加表单
    },
    editProject(project) {
      this.currentProject = {...project};
      this.showEditForm = true;
    },
    async updateHorizontalProject() {
      await axios.put(`/horizontal-projects/${this.currentProject.id}`, this.currentProject); // 更新横向课题信息
      await this.fetchHorizontalProjects(); // 刷新列表数据
      this.showEditForm = false; // 关闭编辑表单
    },
    async deleteProject(id) {
      await axios.delete(`/horizontal-projects/${id}`); // 删除横向课题
      await this.fetchHorizontalProjects(); // 刷新列表数据
    },
    exportToExcel() {
      const ws = XLSX.utils.json_to_sheet(this.horizontalProjects); // 将当前显示的数据转换为工作表
      const wb = XLSX.utils.book_new();
      XLSX.utils.book_append_sheet(wb, ws, "HorizontalProjects");
      XLSX.writeFile(wb, "HorizontalProjects.xlsx"); // 导出为Excel文件
    },
  },
  mounted() {
    this.fetchHorizontalProjects(); // 页面加载时获取数据
  }
};

</script>

<style scoped>
.buttons-container {
  margin-bottom: 20px;
}
</style>