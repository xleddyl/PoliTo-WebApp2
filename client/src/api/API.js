export async function callAPI(method, route, body = undefined, prefix = '') {
    const url = prefix + route

    try {
        const response = await fetch(url, {
            headers: {
                "Content-type": "application/json"
            },
            method,
            body: JSON.stringify(body)
        })

        if (response.ok) {
            return await response.json()
        } else {
            throw new TypeError(await response.text())
        }
    } catch (e) {
        console.log(e)
        throw e
    }
}
