export function Analytics() {
    const sessionStart = Date.now();
    let hasInteracted = false;

    const getDeviceData = () => {
        const ua = navigator.userAgent;
        let browser = "unknown";

        if (ua.includes("Firefox")) browser = "Firefox";
        else if (ua.includes("Edg")) browser = "Edge";
        else if (ua.includes("Chrome")) browser = "Chrome";
        else if (ua.includes("Safari")) browser = "Safari";

        return {
            browser: browser,
            os: navigator.platform,
            device: /Mobi|Android/i.test(ua) ? "Mobile" : "Desktop"
        };
    }
    const initTracker = () => {
        if (typeof window === 'undefined') return;

        sendDeviceData();

        ['click', 'keypress'].forEach(event => {
            document.addEventListener(event, () => { hasInteracted = true; }, { once: true });
        });

        window.addEventListener('beforeunload', () => {
            const payload = JSON.stringify(buildPayload())
            const blob = new Blob([payload], {type: 'application/json'});
            navigator.sendBeacon(
                'https://localhost:8443/api/log-visit',
                blob
            );
        });
    };

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

    const sendManualVisit = async (forceBounce: boolean) => {
        const response = await fetch('https://localhost:8443/api/log-visit', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(buildPayload(forceBounce)),
        });
        return response.ok;
    };

    const sendDeviceData = async () => {
        try {
            await fetch('https://localhost:8443/api/log-device', {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify(getDeviceData())
            });
        } catch (error) {
            console.error("Failed to send device data", error);
        }
    };

    return { initTracker, sendManualVisit };
}