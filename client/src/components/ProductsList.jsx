export default function ProductsList({ products, purchasedProducts=null, addPurchase }) {
   return (
      <div className="flex flex-row">
         <div className="flex-grow">
            {purchasedProducts && <div className="text-white text-lg font-medium pb-2">All Products</div>}
            <div className="relative overflow-x-auto shadow-md rounded-lg w-full">
               <table className="w-full text-sm text-left text-gray-400">
                  <thead className="text-xs uppercase bg-gray-700 text-gray-400">
                     <tr>
                        <th scope="col" className="px-6 py-3">
                           ean
                        </th>
                        <th scope="col" className="px-6 py-3">
                           sku
                        </th>
                        <th scope="col" className="px-6 py-3">
                           name
                        </th>
                        <th scope="col" className="px-6 py-3">
                           brand
                        </th>
                        <th scope="col" className="px-6 py-3">
                           category
                        </th>
                        {purchasedProducts && <th scope="col" className="px-6 py-3 text-center">
                           register purchase
                        </th>}
                     </tr>
                  </thead>
                  <tbody>
                     {products &&
                        products.map((p, i) => (
                           <tr
                              key={p.ean}
                              className={'border-b border-gray-700 ' + (i % 2 === 0 ? 'bg-gray-800' : 'bg-gray-900')}
                           >
                              <td className="px-6 py-4">{p.ean}</td>
                              <td className="px-6 py-4">{p.sku}</td>
                              <td className="px-6 py-4">{p.name}</td>
                              <td className="px-6 py-4">{p.brand}</td>
                              <td className="px-6 py-4">{p.category}</td>
                              {purchasedProducts && <td className="px-6 py-4 flex justify-center">
                                 {purchasedProducts.find((v) => v === p.ean) ? (
                                    <div className="text-gray-600 italic">purchased</div>
                                 ) : (
                                    <svg
                                       xmlns="http://www.w3.org/2000/svg"
                                       className="text-green-800 font-medium cursor-pointer"
                                       fill="currentColor"
                                       height="1em"
                                       viewBox="0 0 448 512"
                                       onClick={() => addPurchase(p)}
                                    >
                                       <path d="M256 80c0-17.7-14.3-32-32-32s-32 14.3-32 32V224H48c-17.7 0-32 14.3-32 32s14.3 32 32 32H192V432c0 17.7 14.3 32 32 32s32-14.3 32-32V288H400c17.7 0 32-14.3 32-32s-14.3-32-32-32H256V80z" />
                                    </svg>
                                 )}
                              </td>}
                           </tr>
                        ))}
                  </tbody>
               </table>
            </div>
         </div>
      </div>
   )
}
