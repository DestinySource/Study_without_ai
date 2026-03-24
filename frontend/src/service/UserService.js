export class UserService {
  async getUserCountry() {
    const res = await fetch('http://localhost:8080/api/country');
    if (!res.ok) {
      throw new Error('Failed to fetch user country');
    }
    return await res.text();
  }

  async getUserFlag() {
    const res = await fetch('http://localhost:8080/api/flag');
    if (!res.ok) {
      throw new Error('Failed to fetch user country');
    }
    return await res.text();
  }
}