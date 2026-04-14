export function Analytics() {
    const sessionStart = Date.now();
    let hasInteracted = false;

    // Initialiseer listeners voor interactie en afsluiten
    const initTracker = () => {
        // Check of we in een browseromgeving zitten
        if (typeof window === 'undefined') return;

        ['click', 'keypress'].forEach(event => {
            document.addEventListener(event, () => { hasInteracted = true; }, { once: true });
        });

        window.addEventListener('beforeunload', () => {
            const payload = JSON.stringify(buildPayload())
            const blob = new Blob([payload], {type: 'application/json'});
            navigator.sendBeacon(
                'http://localhost:8080/api/log-visit',
                blob
            );
        });
    };

    // Bouw de data die we naar de backend sturen
    const buildPayload = (forceBounce?: boolean) => {
        const urlParams = new URLSearchParams(window.location.search);
        const sourceParam = urlParams.get('source');
        const timeOnPage = Math.round((Date.now() - sessionStart) / 1000);

        return {
            page: window.location.pathname,
            referrer: document.referrer || 'Direct',
            source: sourceParam || 'Direct',
            timeOnPage,
            isBounce: forceBounce !== undefined ? forceBounce : !hasInteracted && timeOnPage < 30,
        };
    };

    // Functie voor handmatige test-verzendingen
    const sendManualVisit = async (forceBounce: boolean) => {
        const response = await fetch('http://localhost:8080/api/log-visit', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(buildPayload(forceBounce)),
        });
        return response.ok;
    };

    return { initTracker, sendManualVisit };
}