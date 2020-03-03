import axios from 'axios'
import  qs from 'qs'

/**
 * JSON
 * @param url
 * @param params
 */
export const postJson = (url,params)=>{
    return axios({
        method:'post',
        url:url,
        baseURI:'/api/',
        data:params,
        header:{
        }
    })
}

/**]
 * From
 * @param url
 * @param params
 */
export const postFrom =(url,params)=>{
        return axios ({
            method: 'post',
            url:url,
            baseURI: '/api/',
            data:params,
            transformRequest:[function (data) {
                return qs.stringify(data)
            }],
            header: {
                'Content-Type': 'application/x-www-form-urlencoded'
            }
        })
}
export const getData = (url,params)=>{
    return axios({
        method:'get',
        url:url,
        baseURI: '/api/',
        data:params,
    })
}
