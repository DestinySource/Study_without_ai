import { Service } from '@/services/Service'

export class AnalyticsService extends Service {
  public async logNewVisit(referrer: string, source: string): Promise<boolean> {
    try {
      const response = await fetch('http://localhost:8080/api/log-visit', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json'
        },
        body: JSON.stringify({
          referrer: referrer,
          source: source || 'Direct' // Stuur altijd iets mee
        })
      })
      return response.ok
    } catch (error) {
      console.error('Netwerkfout:', error)
      return false
    }
  }
}
