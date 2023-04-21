export async function callAPI(method, route, body = undefined) {
    const url = '/API' + route

    try {
        const response = await fetch(url, {
            method,
            body
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
