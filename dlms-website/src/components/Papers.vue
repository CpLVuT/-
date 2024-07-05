<template>
  <div>
    <h1>论文管理</h1>
    <div class="buttons-container">
      <el-button type="primary" @click="showCreateForm = true">添加论文</el-button>
      <el-button type="success" @click="exportToExcel">导出为Excel</el-button>
    </div>
    <el-dialog v-model="showCreateForm" title="添加论文" width="80%" >
      <el-form @submit.prevent="createPaper" >
        <el-form-item label="论文标题">
          <el-input v-model="newPaper.paperTitle" placeholder="请输入论文标题" required></el-input>
        </el-form-item>
        <el-form-item label="期刊名称">
          <el-input v-model="newPaper.journalName" placeholder="请输入期刊名称" required></el-input>
        </el-form-item>
        <el-form-item label="期刊号">
          <el-input v-model="newPaper.issueNumber" placeholder="请输入期刊号" required></el-input>
        </el-form-item>
        <el-form-item label="卷号">
          <el-input v-model="newPaper.volumeNumber" placeholder="请输入卷号" required></el-input>
        </el-form-item>
        <el-form-item label="页码">
          <el-input v-model="newPaper.pageNumber" placeholder="请输入页码" required></el-input>
        </el-form-item>
        <el-form-item label="索引级别">
          <el-input v-model="newPaper.indexingLevel" placeholder="请输入索引级别" required></el-input>
        </el-form-item>
        <el-form-item label="作者列表">
          <el-input type="textarea" v-model="newPaper.authorList" placeholder="请输入作者列表" required></el-input>
        </el-form-item>
        <el-form-item label="第一作者">
          <el-input v-model="newPaper.firstAuthor" placeholder="请输入第一作者" required></el-input>
        </el-form-item>
        <el-form-item label="通讯作者">
          <el-input v-model="newPaper.correspondingAuthor" placeholder="请输入通讯作者" required></el-input>
        </el-form-item>
        <el-form-item label="出版日期">
          <el-date-picker v-model="newPaper.publicationDate" type="date" placeholder="选择日期" required></el-date-picker>
        </el-form-item>
        <el-form-item label="影响因子">
          <el-input-number v-model="newPaper.impactFactor" :step="0.01" placeholder="请输入影响因子" required></el-input-number>
        </el-form-item>
        <el-form-item label="论文链接">
          <el-input v-model="newPaper.paperLink" placeholder="请输入论文链接" required></el-input>
        </el-form-item>
        <el-form-item label="提交日期">
          <el-date-picker v-model="newPaper.submissionDate" type="date" placeholder="选择日期" required></el-date-picker>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" native-type="submit">提交</el-button>
          <el-button @click="showCreateForm = false">取消</el-button>
        </el-form-item>
      </el-form>
    </el-dialog>

    <el-table :data="papers" style="width: 100%">
      <el-table-column prop="paperTitle" label="论文标题"></el-table-column>
      <el-table-column prop="journalName" label="期刊名称"></el-table-column>
      <el-table-column prop="issueNumber" label="期刊号"></el-table-column>
      <el-table-column prop="volumeNumber" label="卷号"></el-table-column>
      <el-table-column prop="pageNumber" label="页码"></el-table-column>
      <el-table-column prop="indexingLevel" label="索引级别"></el-table-column>
      <el-table-column prop="authorList" label="作者列表"></el-table-column>
      <el-table-column prop="firstAuthor" label="第一作者"></el-table-column>
      <el-table-column prop="correspondingAuthor" label="通讯作者"></el-table-column>
      <el-table-column prop="publicationDate" label="出版日期"></el-table-column>
      <el-table-column prop="impactFactor" label="影响因子"></el-table-column>
      <el-table-column prop="paperLink" label="论文链接"></el-table-column>
      <el-table-column prop="submissionDate" label="提交日期"></el-table-column>
      <el-table-column>
        <template v-slot="scope">
          <el-button @click="editPaper(scope.row)" type="text">编辑</el-button>
          <el-button @click="deletePaper(scope.row.id)" type="text">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <el-dialog title="编辑论文" v-model:visible="showEditForm">
      <el-form @submit.prevent="updatePaper">
          <el-form-item label="论文标题">
            <el-input v-model="newPaper.paperTitle" placeholder="请输入论文标题" required></el-input>
          </el-form-item>
          <el-form-item label="期刊名称">
            <el-input v-model="newPaper.journalName" placeholder="请输入期刊名称" required></el-input>
          </el-form-item>
          <el-form-item label="期刊号">
            <el-input v-model="newPaper.issueNumber" placeholder="请输入期刊号" required></el-input>
          </el-form-item>
          <el-form-item label="卷号">
            <el-input v-model="newPaper.volumeNumber" placeholder="请输入卷号" required></el-input>
          </el-form-item>
          <el-form-item label="页码">
            <el-input v-model="newPaper.pageNumber" placeholder="请输入页码" required></el-input>
          </el-form-item>
          <el-form-item label="索引级别">
            <el-input v-model="newPaper.indexingLevel" placeholder="请输入索引级别" required></el-input>
          </el-form-item>
          <el-form-item label="作者列表">
            <el-input type="textarea" v-model="currentPaper.authorList" placeholder="请输入作者列表" required></el-input>
          </el-form-item>
          <el-form-item label="第一作者">
            <el-input v-model="currentPaper.firstAuthor" placeholder="请输入第一作者" required></el-input>
          </el-form-item>
          <el-form-item label="通讯作者">
            <el-input v-model="currentPaper.correspondingAuthor" placeholder="请输入通讯作者" required></el-input>
          </el-form-item>
          <el-form-item label="出版日期">
            <el-date-picker v-model="currentPaper.publicationDate" type="date" placeholder="选择日期" required></el-date-picker>
          </el-form-item>
          <el-form-item label="影响因子">
            <el-input-number v-model="currentPaper.impactFactor" :step="0.01" placeholder="请输入影响因子" required></el-input-number>
          </el-form-item>
          <el-form-item label="论文链接">
            <el-input v-model="currentPaper.paperLink" placeholder="请输入论文链接" required></el-input>
          </el-form-item>
          <el-form-item label="提交日期">
            <el-date-picker v-model="currentPaper.submissionDate" type="date" placeholder="选择日期" required></el-date-picker>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" native-type="submit">提交</el-button>
            <el-button @click="showCreateForm = false">取消</el-button>
          </el-form-item>
      </el-form>
    </el-dialog>
  </div>
</template>

<script>
import axios from '@/api/axios';
import * as XLSX from 'xlsx';

export default {
  name: 'PaperManagement',
  data() {
    return {
      papers: [],
      showCreateForm: false,
      showEditForm: false,
      newPaper: {
        paperTitle: '',
        journalName: '',
        issueNumber: '',
        volumeNumber: '',
        pageNumber: '',
        indexingLevel: '',
        authorList: '',
        firstAuthor: '',
        correspondingAuthor: '',
        publicationDate: '',
        impactFactor: '',
        paperLink: '',
        submissionDate: ''
      },
      currentPaper: {}
    };
  },
  methods: {
    fetchPapers() {
      axios.get('/papers').then(response => {
        this.papers = response.data;
      }).catch(error => console.error("Error fetching papers:", error));
    },
    createPaper() {
      axios.post('/papers', this.newPaper).then(() => {
        this.fetchPapers();
        this.showCreateForm = false;
      }).catch(error => console.error("Error creating paper:", error));
    },
    editPaper(paper) {
      this.currentPaper = {...paper};
      this.showEditForm = true;
    },
    updatePaper() {
      axios.put(`/papers/${this.currentPaper.id}`, this.currentPaper).then(() => {
        this.fetchPapers();
        this.showEditForm = false;
      }).catch(error => console.error("Error updating paper:", error));
    },
    deletePaper(id) {
      axios.delete(`/papers/${id}`).then(() => {
        this.fetchPapers();
      }).catch(error => console.error("Error deleting paper:", error));
    },
    exportToExcel() {
      const ws = XLSX.utils.json_to_sheet(this.papers);
      const wb = XLSX.utils.book_new();
      XLSX.utils.book_append_sheet(wb, ws, "Papers");
      XLSX.writeFile(wb, "Papers.xlsx");
    }
  },
  mounted() {
    this.fetchPapers();
  }
};
</script>

<style scoped>
/* 保持样式不变 */
</style>
