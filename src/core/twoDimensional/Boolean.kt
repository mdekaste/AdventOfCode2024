package core.twoDimensional

infix fun Boolean.nand(other: Boolean) = !(this && other)
infix fun Boolean.nor(other: Boolean) = !(this || other)