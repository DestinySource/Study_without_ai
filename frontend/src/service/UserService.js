
const API_BASE = import.meta.env.VITE_API_URL

export class UserService {
  async getUserCountry() {
    const res = await fetch(`${API_BASE}/api/country`);
    if (!res.ok) throw new Error('Failed to fetch user country');
    return res.text();
  }

  async getUserFlag() {
    const res = await fetch(`${API_BASE}/api/flag`);
    if (!res.ok) throw new Error('Failed to fetch user flag');
    return res.text();
    }
  }
