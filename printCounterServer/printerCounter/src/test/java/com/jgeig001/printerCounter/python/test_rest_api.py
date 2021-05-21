import pprint

import requests


def test_getPrinter():
    r = requests.get(url="http://localhost:8080/printer?id=136")
    print(r)
    print(r.text)


def test_getUser():
    r = requests.get(url="http://localhost:8080/user?id=140")
    print(r)
    print(r.text)


def test_newUser():
    r = requests.post(url="http://localhost:8080/newUser?name=Florian Sieber")
    pprint.pprint(r)
    pprint.pprint(r.text)


def test_newPrinter():
    dataX = {
        "name": "WG Printer TS",
        "fOwner": 127,
        "100": 1.4,
        "101": 0.9,
        "102": 0.75,
        "200": 1.2,
        "201": 1.0,
        "202": 0.75,
        "300": 0.5,
        "301": 1.0
    }
    r = requests.post(url="http://localhost:8080/newPrinter", json=dataX)
    print(r)
    print(r.text)


def test_changeUsername():
    r = requests.patch(url="http://localhost:8080/username?id=86&name=Tom Steinhauer")
    print(r)
    print(r.text)


def test_changePrintername():
    r = requests.patch(url="http://localhost:8080/printername?id=107&name=Drucker_TS")
    print(r)
    print(r.text)


def test_newPrintJob():
    url = "http://localhost:8080/newJob?uid=140&pid=138&pages=2&paper=101&quantity=202&color=300"
    r = requests.get(url=url)
    print(r)
    print(r.text)


def test_delPrintJob():
    r = requests.delete(url="http://localhost:8080/delJob?id=133")
    print(r)
    print(r.text)


def test_allPrinterOfUser():
    r = requests.get(url="http://localhost:8080/allPrinters?id=118")
    print(r)
    pprint.pprint(r.text)


def test_delPrinter():
    r = requests.delete(url="http://localhost:8080/delPrinter?id=131")
    print(r)
    pprint.pprint(r.text)


def test_delUser():
    r = requests.delete(url="http://localhost:8080/delUser?id=126")
    print(r)
    pprint.pprint(r.text)


def test_patchCosts():
    data = {
        "100": 17.17,
        "301": None
    }
    r = requests.patch(url="http://localhost:8080/patchCosts?id=138", json=data)
    print(r)
    print(r.text)


def test_allPrinterUsedByUser():
    r = requests.get(url="http://localhost:8080/allPrintersUsedBy?id=140")
    print(r)
    print(r.text)


if __name__ == "__main__":
    test_allPrinterUsedByUser()
