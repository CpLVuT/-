import axios from './axios';

export function getPapers() {
    return axios.get('/papers');
}

export function getPaperById(id) {
    return axios.get(`/papers/${id}`);
}

export function createPaper(paper) {
    return axios.post('/papers', paper);
}

export function updatePaper(id, paper) {
    return axios.put(`/papers/${id}`, paper);
}

export function deletePaper(id) {
    return axios.delete(`/papers/${id}`);
}

export function batchUploadPapers(papers) {
    return axios.post('/papers/batch', papers);
}

export function exportPapersToExcel() {
    return axios.get('/papers/export', { responseType: 'blob' });
}

export function importPapersFromExcel(file) {
    const formData = new FormData();
    formData.append('file', file);
    return axios.post('/papers/import', formData, {
        headers: { 'Content-Type': 'multipart/form-data' }
    });
}
