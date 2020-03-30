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
        url:'/api'+url,
        data:params,
        header:{

        }
    })
};

/**]
 * From
 * @param url
 * @param params
 */
export const postFrom =(url,params)=>{
        return axios ({
            method: 'post',
            url:'/api'+url,
            data:params,
            transformRequest:[function (data) {
                return qs.stringify(data)
            }],
            header: {
                'Content-Type': 'application/x-www-form-urlencoded'
            }
        })
};
export const getData = (url,params)=>{
    return axios({
        method:'get',
        url:'/api'+url,
        data:params,
    })
};
export const getLogout= (url)=>{
    return axios({
            method:'get',
            url:url,

})
};
export const getInfo = (info)=>{
    return axios({
        method:'post',
        url:'/api/i/i',
        data:{
            info:info
        },
        transformRequest:[function (data) {
            return qs.stringify(data)
        }],
        header: {
            'Content-Type': 'application/x-www-form-urlencoded'
        }
    })
};
export const logout= ()=>{
    return axios({
        method:'get',
        url:"/api/logout",
    })
};
export const getJson =(url)=>{
    return axios({
        method:'get',
        url:'/api'+url,
    })
};
export const getDateForm =(url,data)=>{
    return axios({
        method:'get',
        url:'/api'+url,
        data:data,
        transformRequest:[function (data) {
            return qs.stringify(data)
        }],
        header: {
            'Content-Type': 'application/x-www-form-urlencoded'
        }
    })
};
export const download =(url,data)=>{
    return axios({
        method:'post',
        url:'/api'+url,
        data:data,
        transformRequest:[function (data) {
            return qs.stringify(data)
        }],
        responseType: "blob",
        header: {
            'Content-Type': 'application/x-www-form-urlencoded',
            'filename':'utf-8'
        }
    })
};
