[TOC]

# JSON简介

JSON: **J**ava**S**cript **O**bject **N**otation(JavaScript 对象表示法)

JSON 是存储和交换文本信息的语法，是轻量级的文本数据交换格式。类似 XML，但比XML更小、更快、易解析。

JSON 独立于语言：JSON 使用 Javascript语法来描述数据对象，但是 JSON 仍然独立于语言和平台。JSON 解析器和 JSON 库支持许多不同的编程语言。 

## JSON与XML的异同

### 相同之处

* 纯文本
* 具有"自我描述性"（人类可读）
* 具有层级结构（值中存在值）
* 可通过 JavaScript 进行解析
* 数据可使用 AJAX 进行传输

### 不同之处

* JSON没有结束标签
* JSON更短
* JSON读写的速度更快
* JSON能够使用内建的 JavaScript eval() 方法进行解析
* JSON使用数组
* JSON不使用保留字

<span style="color:red">因此JSON比XML更快更易于使用</span>





***



# JSON语法

JSON 语法是 JavaScript 对象表示语法的子集。

* 数据在名称/值对中
* 数据由逗号分隔
* 大括号保存对象
* 中括号保存数组

## JSON 名称/值对

JSON 对象在大括号（{}）中书写：

对象可以包含多个名称/值对：

```json
{ "name":"张三" , "age":23 }
```

也可以写成

```json
{ name:"张三" , age:23 }
```

## JSON值的类型

* 数字（整数或浮点数）
* 字符串（在双引号中）
* 逻辑值（true 或 false）
* 数组（在中括号中）
* 对象（在大括号中）
* null

example: 

```json
{
    erro_code:false,
    persons:[
        {"name":"张三", "age":23, "gender":"男"},
		{"name":"李四", "age":25, "gender":"女"}],
    num: 2,
}
```

数组与对象都能多层嵌套

# 原生JS处理JSON

## 1. 对象

### 1.1 访问对象值

通过**obj.keyword**访问

```js
var person = { "name":"张三", "age":21, "gender":"男" ,"work":null};
var x = person.name;
var y = person["name"] // x==y --> true
```

等效与**obj[keyword]**

**for key in jsonObj** 循环访问对象值

```js
var person = { "name":"张三", "age":21, "gender":"男" ,"work":null};
for (key in person){
    console.log(key + ": "+ person[key])// 注意这里不能用person.key
}
/*	控制台结果：
	name: 张三
	age: 21
	gender: 男
	work: null
*/
```

### 1.2 嵌套对象访问值

```js
var p = {
    erro_code:false,
    person : {"name":"张三", "age":23, "gender":"男"},
    num: 2,
};
var name = p.person.name;
var age = p["person"]["age"];
```

### 1.3 修改/添加值

#### 修改值

```js
var p = {
    erro_code:false,
    person : {"name":"张三", "age":23, "gender":"男"},
    num: 2,
};
p.person.name = "李四";
p["person"]["age"] = 25;
console.log(JSON.stringify(p)); 
/*
"{"erro_code":false,"person":{"name":"李四","age":25,"gender":"男"},"num":2}"
*/
```

#### 添加值

```js
var p = {"name":"张三"};
p.age = 23;
p["gender"] = "男";
// p --> {"name":"张三", "age":23, "gender":"男"}
```



### 1.4 删除对象属性

```js
var p = {
    erro_code:false,
    person : {"name":"张三", "age":23, "gender":"男"},
    num: 2
};
delete p.person.name 
// p-->{erro_code:false,person:{"age":23, "gender":"男"},num: 2};

delete p["person"]["age"] 
// p-->{erro_code:false,person:{"gender":"男"},num: 2};
```



## 2 数组

JSON 中数组值必须是合法的 JSON 数据类型（字符串, 数字, 对象, 数组, 布尔值或 null）

###  2.1 访问值

array[index]

通过索引访问

```js
var site = {
    "name":"网站",
    "num":3,
    "sites":[ "Google", "Runoob", "Taobao" ]
}
var x = site.sites[1] // x --> "Runoob"
```

### 2.2 修改值

```js
var p = {
    erro_code:false,
    persons:[
        {"name":"张三", "age":23, "gender":"男"},
        {"name":"李四", "age":25, "gender":"女"}],
    num: 2,
}
p.persons[1]["name"] = "王五";
```

### 2.3 删除数组元素

```js
var p = {
    erro_code:false,
    persons:[
        {"name":"张三", "age":23, "gender":"男"},
        {"name":"李四", "age":25, "gender":"女"}],
    num: 2,
}
delete p.persons[0]
//delete 删除后保留该下标，值为undefined
// p.persons --> [undefined, {"name":"李四", "age":25, "gender":"女"}]
//彻底删除 p.person.splice(0,0)
```

## 3. JSON.parse()

JSON 通常用于与服务端交换数据。

在接收服务器数据时一般是字符串。

我们可以使用 JSON.parse() 方法将数据转换为 JavaScript 对象。

```js
JSON.parse(text[, reviver])
```

* **text:**必需， 一个有效的 JSON 字符串。
* **reviver:** 可选，一个转换结果的函数， 将为对象的每个成员调用此函数。

```js
var person =  '{ "name":"张三", "age":21, "gender":"男" ,"work":null}';
var jsonObj = JSON.parse(person);
```

注意：JSON不能存储Date，因此后端传数据时应该传字符串

## 4. JSON.stringify()

 使用 JSON.stringify() 方法将 JavaScript 对象转换为字符串

```js
JSON.stringify(value[, replacer[, space]])
```

* value:

  必需， 要转换的 JavaScript 值（通常为对象或数组）。

* replacer:

  可选。用于转换结果的函数或数组。

  如果 replacer 为函数，则 JSON.stringify 将调用该函数，并传入每个成员的键和值。使用返回值而不是原始值。如果此函数返回 undefined，则排除成员。根对象的键是一个空字符串：""。

  如果 replacer 是一个数组，则仅转换该数组中具有键值的成员。成员的转换顺序与键在数组中的顺序一样。当 value 参数也为数组时，将忽略 replacer 数组。

* space:

  可选，文本添加缩进、空格和换行符，如果 space 是一个数字，则返回值文本在每个级别缩进指定数目的空格，如果 space 大于 10，则文本缩进 10 个空格。space 也可以使用非数字，如：\t。

```js
var arr = [ "Google", "Runoob", "Taobao", "Facebook" ]
var json = JSON.stringify(arr);
```



### Java 处理JSON

