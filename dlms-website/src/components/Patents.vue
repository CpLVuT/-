<template>
  <div>
    <h1>专利管理</h1>
    <el-button type="primary" @click="showCreateForm = true">添加专利</el-button>
    <el-button type="success" @click="exportToExcel">导出为Excel</el-button>

    <el-dialog title="添加专利" v-model:visible="showCreateForm" width="50%">
      <el-form @submit.prevent="createPatent">
        <el-form-item label="专利名称">
          <el-input v-model="newPatent.patentName" placeholder="请输入专利名称" required></el-input>
        </el-form-item>
        <el-form-item label="专利属性">
          <el-input v-model="newPatent.patentAttribute" placeholder="请输入专利属性" required></el-input>
        </el-form-item>
        <el-form-item label="专利类型">
          <el-input v-model="newPatent.patentType" placeholder="请输入专利类型" required></el-input>
        </el-form-item>
        <el-form-item label="授权状态">
          <el-input v-model="newPatent.authorizationStatus" placeholder="请输入授权状态" required></el-input>
        </el-form-item>
        <el-form-item label="申请号">
          <el-input v-model="newPatent.applicationNumber" placeholder="请输入申请号" required></el-input>
        </el-form-item>
        <el-form-item label="公布号">
          <el-input v-model="newPatent.publicationNumber" placeholder="请输入公布号" required></el-input>
        </el-form-item>
        <el-form-item label="授权号">
          <el-input v-model="newPatent.authorizationNumber" placeholder="请输入授权号" required></el-input>
        </el-form-item>
        <el-form-item label="发明人">
          <el-input v-model="newPatent.inventor" placeholder="请输入发明人" required></el-input>
        </el-form-item>
        <el-form-item label="专利权人">
          <el-input v-model="newPatent.patentee" placeholder="请输入专利权人" required></el-input>
        </el-form-item>
        <el-form-item label="申请日期">
          <el-date-picker v-model="newPatent.applicationDate" type="date" placeholder="选择日期" required></el-date-picker>
        </el-form-item>
        <el-form-item label="授权日期">
          <el-date-picker v-model="newPatent.authorizationDate" type="date" placeholder="选择日期" required></el-date-picker>
        </el-form-item>
        <el-form-item label="专利证书">
          <el-input v-model="newPatent.patentCertificate" placeholder="请输入专利证书" required></el-input>
        </el-form-item>
        <el-form-item label="提交日期">
          <el-date-picker v-model="newPatent.submissionDate" type="date" placeholder="选择日期" required></el-date-picker>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" native-type="submit">提交</el-button>
          <el-button @click="showCreateForm = false">取消</el-button>
        </el-form-item>
      </el-form>
    </el-dialog>

    <el-table :data="patents" style="width: 100%" v-if="patents.length > 0">
      <el-table-column prop="patentName" label="专利名称"></el-table-column>
      <el-table-column prop="patentAttribute" label="专利属性"></el-table-column>
      <el-table-column prop="patentType" label="专利类型"></el-table-column>
      <el-table-column prop="authorizationStatus" label="授权状态"></el-table-column>
      <el-table-column prop="applicationNumber" label="申请号"></el-table-column>
      <el-table-column prop="publicationNumber" label="公布号"></el-table-column>
      <el-table-column prop="authorizationNumber" label="授权号"></el-table-column>
      <el-table-column prop="inventor" label="发明人"></el-table-column>
      <el-table-column prop="patentee" label="专利权人"></el-table-column>
      <el-table-column prop="applicationDate" label="申请日期"></el-table-column>
      <el-table-column prop="authorizationDate" label="授权日期"></el-table-column>
      <el-table-column prop="patentCertificate" label="专利证书"></el-table-column>
      <el-table-column prop="submissionDate" label="提交日期"></el-table-column>
      <el-table-column>
        <template v-slot="scope">
          <el-button @click="editPatent(scope.row)" type="text">编辑</el-button>
          <el-button @click="deletePatent(scope.row.id)" type="text">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <el-dialog title="编辑专利" v-model:visible="showEditForm" width="50%">
      <el-form @submit.prevent="updatePatent">
        <el-form-item label="专利名称">
          <el-input v-model="currentPatent.patentName" placeholder="请输入专利名称" required></el-input>
        </el-form-item>
        <el-form-item label="专利属性">
          <el-input v-model="currentPatent.patentAttribute" placeholder="请输入专利属性" required></el-input>
        </el-form-item>
        <el-form-item label="专利类型">
          <el-input v-model="currentPatent.patentType" placeholder="请输入专利类型" required></el-input>
        </el-form-item>
        <el-form-item label="授权状态">
          <el-input v-model="currentPatent.authorizationStatus" placeholder="请输入授权状态" required></el-input>
        </el-form-item>
        <el-form-item label="申请号">
          <el-input v-model="currentPatent.applicationNumber" placeholder="请输入申请号" required></el-input>
        </el-form-item>
        <el-form-item label="公布号">
          <el-input v-model="currentPatent.publicationNumber" placeholder="请输入公布号" required></el-input>
        </el-form-item>
        <el-form-item label="授权号">
          <el-input v-model="currentPatent.authorizationNumber" placeholder="请输入授权号" required></el-input>
        </el-form-item>
        <el-form-item label="发明人">
          <el-input v-model="currentPatent.inventor" placeholder="请输入发明人" required></el-input>
        </el-form-item>
        <el-form-item label="专利权人">
          <el-input v-model="currentPatent.patentee" placeholder="请输入专利权人" required></el-input>
        </el-form-item>
        <el-form-item label="申请日期">
          <el-date-picker v-model="currentPatent.applicationDate" type="date" placeholder="选择日期" required></el-date-picker>
        </el-form-item>
        <el-form-item label="授权日期">
          <el-date-picker v-model="currentPatent.authorizationDate" type="date" placeholder="选择日期" required></el-date-picker>
        </el-form-item>
        <el-form-item label="专利证书">
          <el-input v-model="currentPatent.patentCertificate" placeholder="请输入专利证书" required></el-input>
        </el-form-item>
        <el-form-item label="提交日期">
          <el-date-picker v-model="currentPatent.submissionDate" type="date" placeholder="选择日期" required></el-date-picker>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" native-type="submit">提交</el-button>
          <el-button @click="showEditForm = false">取消</el-button>
        </el-form-item>
      </el-form>
    </el-dialog>
  </div>
</template>

<script>
import axios from '@/api/axios';
import * as XLSX from 'xlsx';

export default {
  name: 'PatentManagement',
  data() {
    return {
      patents: [],
      showCreateForm: false,
      showEditForm: false,
      newPatent: {
        patentName: '',
        patentAttribute: '',
        patentType: '',
        authorizationStatus: '',
        applicationNumber: '',
        publicationNumber: '',
        authorizationNumber: '',
        inventor: '',
        patentee: '',
        applicationDate: '',
        authorizationDate: '',
        patentCertificate: '',
        submissionDate: ''
      },
      currentPatent: {}
    };
  },
  methods: {
    fetchPatents() {
      axios.get('/patents').then(response => {
        this.patents = response.data;
      }).catch(error => console.error("Error fetching patents:", error));
    },
    createPatent() {
      axios.post('/patents', this.newPatent).then(() => {
        this.fetchPatents();
        this.showCreateForm = false;
      }).catch(error => console.error("Error creating patent:", error));
    },
    editPatent(patent) {
      this.currentPatent = {...patent};
      this.showEditForm = true;
    },
    updatePatent() {
      axios.put(`/patents/${this.currentPatent.id}`, this.currentPatent).then(() => {
        this.fetchPatents();
        this.showEditForm = false;
      }).catch(error => console.error("Error updating patent:", error));
    },
    deletePatent(id) {
      axios.delete(`/patents/${id}`).then(() => {
        this.fetchPatents();
      }).catch(error => console.error("Error deleting patent:", error));
    },
    exportToExcel() {
      const ws = XLSX.utils.json_to_sheet(this.patents);
      const wb = XLSX.utils.book_new();
      XLSX.utils.book_append_sheet(wb, ws, "Patents");
      XLSX.writeFile(wb, "Patents.xlsx");
    }
  },
  mounted() {
    this.fetchPatents();
  }
};
</script>

<style scoped>
/* Add your style here */
</style>
