def fizzbuzz(fizz, buzz):
  def fizzbuzzer(i):
    r = ''
    if fizz(i):
      r += 'Fizz'
    if buzz(i):
      r += 'Buzz'
    return r if r else i
  return fizzbuzzer

if __name__ == '__main__':
  [print(e) for e in map(fizzbuzz(lambda n: not n % 3, lambda n: not n % 5), range(1,101))]
  [print(e) for e in map(fizzbuzz(lambda n: not n % 3 or '3' in str(n), lambda n: not n % 5 or '5' in str(n)), range(1,101))]
