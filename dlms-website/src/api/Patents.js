import axios from './axios';

export function getPatents() {
    return axios.get('/patents');
}

export function getPatentById(id) {
    return axios.get(`/patents/${id}`);
}

export function createPatent(patent) {
    return axios.post('/patents', patent);
}

export function updatePatent(id, patent) {
    return axios.put(`/patents/${id}`, patent);
}

export function deletePatent(id) {
    return axios.delete(`/patents/${id}`);
}

export function batchUploadPatents(patents) {
    return axios.post('/patents/batch', patents);
}

export function exportPatentsToExcel() {
    return axios.get('/patents/export', { responseType: 'blob' });
}

export function importPatentsFromExcel(file) {
    const formData = new FormData();
    formData.append('file', file);
    return axios.post('/patents/import', formData, {
        headers: { 'Content-Type': 'multipart/form-data' }
    });
}
