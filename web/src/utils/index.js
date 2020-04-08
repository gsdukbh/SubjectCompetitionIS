/**
 *
 * @param {(Object)} time
 * @param {string} cFormat
 * @returns {string | null}
 */
export function parseTime(time, cFormat) {
    if (arguments.length === 0) {
        return null
    }
    const format = cFormat || '{y}-{m}-{d} {h}:{i}:{s}';
    let date;
    date = new Date(time);
    const formatObj = {
        y: date.getFullYear(),
        m: date.getMonth() + 1,
        d: date.getDate(),
        h: date.getHours(),
        i: date.getMinutes(),
        s: date.getSeconds(),
        a: date.getDay()
    };
    return format.replace(/{([ymdhisa])+}/g, (result, key) => {
        const value = formatObj[key];

        if (key === 'a') {
            return ['日', '一', '二', '三', '四', '五', '六'][value];
        }
        return value.toString().padStart(2, '0');
    });
}

/**
 *
 * @param str
 * @returns {RegExpExecArray & {groups: {}}}
 */
export function isDigit(str) {
    const reg = /^[0-9]{1,20}$/;
    return reg.test(str);
}

/**
 *
 * @param str
 * @returns {boolean}
 */
export function isRegisterUserName(str) {
    const reg = /^[0-9a-zA-Z]{4,20}$/;
    return reg.test(str)
}

/**
 * 密码最少6位，包括至少1个大写字母，1个小写字母，1个数字，
 * @param str
 * @returns {boolean}
 */
export function isPassword(str) {
    const reg = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)[^]{6,16}$/;
    return reg.test(str)
}

/**
 *
 * @param str
 * @returns {boolean}
 */
export function isId(str) {
    const reg = /^[1-9]\d{5}(18|19|([23]\d))\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\d{3}[0-9Xx]$/;
    return reg.test(str)
}

export function resetPwd() {
    return String.fromCharCode(randomNum(65, 90)) + String.fromCharCode(randomNum(97, 122)) + randomNum(0, 10) + "" + randomNum(0, 10) + "" + randomNum(0, 10) + "" + randomNum(0, 10);
}

function randomNum(minNum, maxNum) {
    switch (arguments.length) {
        case 1:
            return parseInt(Math.random() * minNum + 1, 10);

        case 2:
            return parseInt(Math.random() * (maxNum - minNum + 1) + minNum, 10);

        default:
            return 0;

    }
}
