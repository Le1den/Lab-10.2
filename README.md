|B251871022||T.Tugsbayar|

Lab 10.2 - Testleh

Hiisen zuil

1-r heseg: Specification Testing (LinkedIntQueue)
- IntQueue interface-d undeslej LinkedIntQueue-d testуud bichsen
- Testuud: isEmpty, enqueue, dequeue, peek, size, clear

2-r heseg: Structural Testing (ArrayIntQueue)
- ArrayIntQueue-d **3 aldaa** olj zassan
- **100% line coverage** hursun

ArrayIntQueue-d zassan aldaanuud


1  `isEmpty()` | `size >= 0` uurged true gardag | `size == 0` bolgov |
2  `peek()` | Hooson ued null butsaahgui baisaan | `if (isEmpty()) return null` nemev |
3  `ensureCapacity()` | Buruu index togtoogdoj baisaan | `oldCapacity - head + i` bolgov |

 Test-iin ur dun
- Test: **18**
- Failure: **0**
- Error: **0**
- Line Coverage: **100%**
- 
 Ajilluulah arga

```bash
mvn test
```

```bash
mvn site
```

Duussanii daraa `target/site/jacoco/index.html`-g brauzerт neej coverage tailang harна.
