def fizzbuzz(fizz, buzz):
  for i in range(1,101):
    r = ''
    if fizz(i):
      r += 'Fizz'
    if buzz(i):
      r += 'Buzz'
    print(r if r else i)

if __name__ == '__main__':
  fizzbuzz(lambda n: not n % 3, lambda n: not n % 5)
  fizzbuzz(lambda n: not n % 3 or '3' in str(n), lambda n: not n % 5 or '5' in str(n))
