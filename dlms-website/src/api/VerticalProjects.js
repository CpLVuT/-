import axios from './axios';

export function getVerticalProjects() {
    return axios.get('/vertical-projects');
}

export function getVerticalProjectById(id) {
    return axios.get(`/vertical-projects/${id}`);
}

export function createVerticalProject(project) {
    return axios.post('/vertical-projects', project);
}

export function updateVerticalProject(id, project) {
    return axios.put(`/vertical-projects/${id}`, project);
}

export function deleteVerticalProject(id) {
    return axios.delete(`/vertical-projects/${id}`);
}

export function batchUploadVerticalProjects(projects) {
    return axios.post('/vertical-projects/batch', projects);
}

export function exportVerticalProjectsToExcel() {
    return axios.get('/vertical-projects/export', { responseType: 'blob' });
}

export function importVerticalProjectsFromExcel(file) {
    const formData = new FormData();
    formData.append('file', file);
    return axios.post('/vertical-projects/import', formData, {
        headers: { 'Content-Type': 'multipart/form-data' }
    });
}
