
class UserService {
  constructor(http) {
    this.http = http;
    this.apiUrl = 'http://localhost:6060/api/user';
    this.apiUrl = 'http://localhost:6060/api/position';
    this.apiUrl = 'http://localhost:6060/api/login';
  }

  addUser(user) {
    return this.http.post(`${this.apiUrl}/create`, user);
  }

  login(users){
    return this.http.post(`${this.apiUrl}/login`, users);
  }

  getUsers() {
    return this.http.get(`${this.apiUrl}/users`);
  }

  getUser(userNo) {
    return this.http.get(`${this.apiUrl}/user/${userNo}`);
  }

  updateUser(userNo, user) {
    return this.http.put(`${this.apiUrl}/user/${userNo}`, user);
  }

  deleteUser(userNo) {
    return this.http.delete(`${this.apiUrl}/user/${userNo}`);
  }
}

// Export the UserService as a module
export default UserService;
