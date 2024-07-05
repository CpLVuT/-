import axios from './axios';

export function getPendingPapers() {
    return axios.get('/review/papers');
}

export function getPendingPatents() {
    return axios.get('/review/patents');
}

export function getPendingHorizontalProjects() {
    return axios.get('/review/horizontal-projects');
}

export function getPendingVerticalProjects() {
    return axios.get('/review/vertical-projects');
}

export function updatePaperReviewStatus(id, status) {
    return axios.post(`/review/papers/${id}`, { status });
}

export function updatePatentReviewStatus(id, status) {
    return axios.post(`/review/patents/${id}`, { status });
}

export function updateHorizontalProjectReviewStatus(id, status) {
    return axios.post(`/review/horizontal-projects/${id}`, { status });
}

export function updateVerticalProjectReviewStatus(id, status) {
    return axios.post(`/review/vertical-projects/${id}`, { status });
}