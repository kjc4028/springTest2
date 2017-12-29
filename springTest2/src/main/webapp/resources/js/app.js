/*app.js*/

/*App객체 생성*/
var App = window.App || {};

App.test = function(str){
alert(str);
};

/*데이터 등록*/
App.insert = function(){
	var data =[];
	var form = App.makeForm('insert');
	var valuecnt  = $('.inputData').find('input[type=text]');
	var value = '';
	for (var i = 0; i < valuecnt.length; i++) {
	value = $('.inputData').find('input[type=text]');
	data.push(value);
	}
	form.append(data);
	form.hide();
	$('body').append(form);
	form.submit();
};

App.updateForm = function(target){
	App.cancel();
	
	var baseData = $(target).parents('tr').find('.dataTemplate :hidden');//입력되어있는 기존 히든 데이터타입?
	var updateForm = $('#hiddenTR').clone();
	console.log('baseData: '+baseData);
	
	$(updateForm).attr('id', 'updateForm');
	for(var i=0; i<baseData.length; i++){
		var name = $(baseData[i]).attr('name');
		var data = $(target).parents('td').find(':text[name=' + name + '],:hidden[name=' + name + '],span[id=' + name + ']').val();
			$(updateForm).find(':text[name=' + name + '],:hidden[name=' + name + ']').val(data);
			$(updateForm).find('span[id='+name+']').text(data);
			console.log('name: '+name);
			console.log('data: ' + data);
		}
	$(target).parents('tr').hide();
	$(target).parents('tr').attr('id', 'preData');
	$(target).parents('tr').after(updateForm);
	$(updateForm).show();
};

App.update = function(){
	var data = [];
	var value ='';
	var form = App.makeForm('update');
	var  updateData = $('#updateForm').find('td input[type=text], td input[type=hidden]');
	for (var i = 0; i < updateData.length; i++) {
		value = $('#updateForm').find('td input[type=text], td input[type=hidden]');
		data.push(value);
		}
	form.append(data);
	form.hide();
	$('body').append(form);
	form.submit();
};

App.deleteData = function(target){
	var data = $(target).parents('tr').find('.dataTemplate :hidden');
	var delData=[];
	$(data).each(function(i, value){
		var name = $(data).attr('name');
		var data2 = $(target).parents('td').find('.dataTemplate :hidden[name='+name+']');
		delData.push(data2);
		console.log(data2.serialize());
	});
	var form = App.makeForm('delete');
	form.append(delData);
	$('body').append(form);
	form.submit();
};

App.cancel = function(){
	$('#updateForm').remove();
	var preData = $('.table').find('#preData');
	preData.show();
	preData.attr('id','');
};

/*폼 생성*/
App.makeForm = function(crud){
	var form = $('<form />');
	
	form.attr('name','testFrm')
		.attr('action','/test/'+crud)
		.attr('method','post');
	
	return form;
};