function format(num, suffix) {
	if (num != num) {
		return '--';
	}
	return ((Math.round(num * 100) / 100).toFixed(2) + '').replace(
			/\d{1,3}(?=(\d{3})+(\.\d*)?$)/g, '$&,')
			+ (suffix || '');
}
function format1(num) {
	return (Math.round(num) + '').replace(/\d{1,3}(?=(\d{3})+(\.\d*)?$)/g,
			'$&,');
}

var feeTime, currNum = 0, targetNum = 0, step = 1;

/* 今日保费变化动画 */
function computeOrder() {
	feeTime = setInterval(function() {
		if (targetNum == currNum) {
			clearInterval(feeTime);
			feeTime = false;
		} else {
			step = Math.ceil((targetNum - currNum) / 10);
			currNum += step;
			$("#numberRun").html(format1(currNum));
		}
	}, 2);
}

/* 今日保费最新获取保费值 */
function setCurrent(num) {
	feeTime && clearInterval(feeTime);
	targetNum = num;
	computeOrder();
}


var feeTime1, currNum1 = 0, targetNum1 = 0, step1 = 1;

/* 今日签单保费变化动画 */
function computeOrder1() {
	feeTime1 = setInterval(function() {
		if (targetNum1 == currNum1) {
			clearInterval(feeTime1);
			feeTime1 = false;
		} else {
			step1 = Math.ceil((targetNum1 - currNum1) / 10);
			currNum1 += step1;
			$("#numberRun1").html(format1(currNum1));
		}
	}, 2);
}

/* 今日签单最新获取保费值 */
function setCurrent1(num) {
	feeTime1 && clearInterval(feeTime1);
	targetNum1 = num;
	computeOrder1();
}