<template>
  <div>
    <h1>纵向课题</h1>
    <div class="buttons-container">
      <el-button type="primary" @click="showCreateForm = true">添加纵向课题</el-button>
      <el-button type="success" @click="exportToExcel">导出为Excel</el-button>
    </div>
    <el-dialog v-model="showCreateForm" title="添加纵向课题" width="80%">
      <el-form @submit.prevent="createVerticalProject" label-width="120px" class="demo-ruleForm">
        <el-form-item label="项目名称 :">
          <el-input v-model="newProject.projectName" placeholder="请输入项目名称"></el-input>
        </el-form-item>
        <el-form-item label="项目编号 :">
          <el-input v-model="newProject.projectNumber" placeholder="请输入项目编号"></el-input>
        </el-form-item>
        <el-form-item label="项目级别 :">
          <el-input v-model="newProject.projectLevel" placeholder="请输入项目级别"></el-input>
        </el-form-item>
        <el-form-item label="总金额 :">
          <el-input v-model="newProject.totalAmount" type="number" placeholder="请输入总金额"></el-input>
        </el-form-item>
        <el-form-item label="审批部门 :">
          <el-input v-model="newProject.approvalDepartment" placeholder="请输入审批部门"></el-input>
        </el-form-item>
        <el-form-item label="项目类型 :">
          <el-input v-model="newProject.projectType" placeholder="请输入项目类型"></el-input>
        </el-form-item>
        <el-form-item label="项目周期 :">
          <el-input v-model="newProject.duration" placeholder="请输入项目周期"></el-input>
        </el-form-item>
        <el-form-item label="经费记录 :">
          <el-input v-model="newProject.fundingRecord" placeholder="请输入经费记录"></el-input>
        </el-form-item>
        <el-form-item label="参与人员 :">
          <el-input v-model="newProject.participants" placeholder="请输入参与人员"></el-input>
        </el-form-item>
        <el-form-item label="项目负责人 :">
          <el-input v-model="newProject.projectLeader" placeholder="请输入项目负责人"></el-input>
        </el-form-item>
        <el-form-item label="财务账号 :">
          <el-input v-model="newProject.financialAccount" placeholder="请输入财务账号"></el-input>
        </el-form-item>
        <el-form-item label="审批文件 :">
          <el-input v-model="newProject.approvalDocument" placeholder="请输入审批文件"></el-input>
        </el-form-item>
        <el-form-item label="完成证明 :">
          <el-input v-model="newProject.completionCertificate" placeholder="请输入完成证明"></el-input>
        </el-form-item>
        <el-form-item label="完成报告 :">
          <el-input v-model="newProject.completionReport" placeholder="请输入完成报告"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="createVerticalProject">提交</el-button>
          <el-button @click="showCreateForm = false">取消</el-button>
        </el-form-item>
      </el-form>
    </el-dialog>

    <el-table :data="verticalProjects" style="width: 100%" v-if="verticalProjects.length > 0">
      <el-table-column prop="projectName" label="项目名称"></el-table-column>
      <el-table-column prop="projectNumber" label="项目编号"></el-table-column>
      <el-table-column prop="projectLevel" label="项目级别"></el-table-column>
      <el-table-column prop="totalAmount" label="总金额"></el-table-column>
      <el-table-column prop="approvalDepartment" label="审批部门"></el-table-column>
      <el-table-column prop="projectType" label="项目类型"></el-table-column>
      <el-table-column prop="duration" label="项目周期"></el-table-column>
      <el-table-column prop="fundingRecord" label="经费记录"></el-table-column>
      <el-table-column prop="participants" label="参与人员"></el-table-column>
      <el-table-column prop="projectLeader" label="项目负责人"></el-table-column>
      <el-table-column prop="financialAccount" label="财务账号"></el-table-column>
      <el-table-column prop="completionCertificate" label="完成证明"></el-table-column>
      <el-table-column prop="completionReport" label="完成报告"></el-table-column>
      <el-table-column label="操作">
        <template v-slot="scope">
          <el-button @click="editProject(scope.row)" type="text" circle size="small">编辑</el-button>
          <el-button @click="deleteProject(scope.row.id)" type="text" circle size="small">删除</el-button>
        </template>
      </el-table-column>
    </el-table>
  </div>
</template>

<script>
import axios from '../api/axios'; // 根据实际项目路径调整
import * as XLSX from 'xlsx'; // 根据您的项目配置正确导入xlsx

export default {
  data() {
    return {
      verticalProjects: [],
      showCreateForm: false,
      newProject: {
        projectName: '',
        projectNumber: '',
        projectLevel: '',
        totalAmount: '',
        approvalDepartment: '',
        projectType: '',
        duration: '',
        fundingRecord: '',
        participants: '',
        projectLeader: '',
        financialAccount: '',
        approvalDocument: '',
        completionCertificate: '',
        completionReport: ''
      },
      currentProject: {}
    };
  },
  methods: {
    async fetchVerticalProjects() {
      const response = await axios.get('/vertical-projects');
      this.verticalProjects = response.data;
    },
    async createVerticalProject() {
      await axios.post('/vertical-projects', this.newProject);
      await this.fetchVerticalProjects();
      this.showCreateForm = false;
    },
    editProject(project) {
      this.currentProject = {...project};
      this.showEditForm = true;
    },
    async updateVerticalProject() {
      await axios.put(`/vertical-projects/${this.currentProject.id}`, this.currentProject);
      await this.fetchVerticalProjects();
      this.showEditForm = false;
    },
    async deleteProject(id) {
      await axios.delete(`/vertical-projects/${id}`);
      await this.fetchVerticalProjects();
    },
    exportToExcel() {
      const ws = XLSX.utils.json_to_sheet(this.verticalProjects);
      const wb = XLSX.utils.book_new();
      XLSX.utils.book_append_sheet(wb, ws, "VerticalProjects");
      XLSX.writeFile(wb, "VerticalProjects.xlsx");
    },

  },
  mounted() {
    this.fetchVerticalProjects();
  }
};
</script>

<style scoped>
.buttons-container {
  margin-bottom: 20px;
}
</style>
