package core

fun<T1, T2, R> withNoNulls(arg1: T1?, arg2: T2?, block: (T1, T2) -> R): R? = arg1?.let { a1 -> arg2?.let { a2 -> block(a1, a2) } }
fun<T1, T2, T3, R> withNoNulls(arg1: T1?, arg2: T2?, arg3: T3?, block: (T1, T2, T3) -> R): R? = arg1?.let { a1 -> arg2?.let { a2 -> arg3?.let { a3 -> block(a1, a2, a3) } } }
