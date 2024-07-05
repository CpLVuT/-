import axios from './axios';

export function getReviewRecords() {
    return axios.get('/review-records');
}

export function getReviewRecordById(id) {
    return axios.get(`/review-records/${id}`);
}

export function createReviewRecord(record) {
    return axios.post('/review-records', record);
}

export function updateReviewRecord(id, record) {
    return axios.put(`/review-records/${id}`, record);
}

export function deleteReviewRecord(id) {
    return axios.delete(`/review-records/${id}`);
}

export function batchUploadReviewRecords(records) {
    return axios.post('/review-records/batch', records);
}

export function exportReviewRecordsToExcel() {
    return axios.get('/review-records/export', { responseType: 'blob' });
}

export function importReviewRecordsFromExcel(file) {
    const formData = new FormData();
    formData.append('file', file);
    return axios.post('/review-records/import', formData, {
        headers: { 'Content-Type': 'multipart/form-data' }
    });
}
