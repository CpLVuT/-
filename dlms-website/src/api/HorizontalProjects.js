import axios from './axios';

export function getHorizontalProjects() {
    return axios.get('/horizontal-projects');
}

export function getHorizontalProjectById(id) {
    return axios.get(`/horizontal-projects/${id}`);
}

export function createHorizontalProject(project) {
    return axios.post('/horizontal-projects', project);
}

export function updateHorizontalProject(id, project) {
    return axios.put(`/horizontal-projects/${id}`, project);
}

export function deleteHorizontalProject(id) {
    return axios.delete(`/horizontal-projects/${id}`);
}

export function batchUploadHorizontalProjects(projects) {
    return axios.post('/horizontal-projects/batch', projects);
}

export function exportHorizontalProjectsToExcel() {
    return axios.get('/horizontal-projects/export', { responseType: 'blob' });
}

export function importHorizontalProjectsFromExcel(file) {
    const formData = new FormData();
    formData.append('file', file);
    return axios.post('/horizontal-projects/import', formData, {
        headers: { 'Content-Type': 'multipart/form-data' }
    });
}
export function getApprovedProjects() {
    return axios.get(`/horizontal-projects/approved`);
}
