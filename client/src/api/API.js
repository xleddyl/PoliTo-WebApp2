export async function callAPI(method, route, body = undefined, prefix = '') {
   const url = prefix + route

   try {
      const response = await fetch(url, {
         headers: {
            'Content-type': 'application/json',
         },
         method,
         body: JSON.stringify(body),
      })

      if (response.ok) {
         if (response.headers.get("'Content-type") === 'application/json') {
            return await response.json()
         } else {
            return await response.text()
         }
      } else {
         throw new TypeError(await response.text())
      }
   } catch (e) {
      console.log(e)
      throw e
   }
}
