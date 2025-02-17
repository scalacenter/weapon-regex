package weaponregex

import weaponregex.constant.ErrorMessage
import weaponregex.extension.EitherExtension.LeftStringEitherTest
import weaponregex.model.mutation.Mutant
import weaponregex.mutator.BuiltinMutators

class WeaponRegeXTest extends munit.FunSuite {
  test("Can mutate without options") {
    val mutations = WeaponRegeX.mutate("^a").getOrFail

    assert(mutations.isInstanceOf[Seq[Mutant]])
    assertEquals(mutations.length, 2)
  }

  test("Can mutate with only mutators as option") {
    val mutations = WeaponRegeX.mutate("^a", BuiltinMutators.all).getOrFail

    assert(mutations.isInstanceOf[Seq[Mutant]])
    assertEquals(mutations.length, 2)
  }

  test("Can mutate with empty sequence of mutators as option") {
    val mutations = WeaponRegeX.mutate("^a", Nil).getOrFail

    assert(mutations.isInstanceOf[Seq[Mutant]])
    assertEquals(mutations, Nil)
  }

  test("Can mutate with only levels as option") {
    val mutations = WeaponRegeX.mutate("^a", mutationLevels = Seq(1)).getOrFail

    assert(mutations.isInstanceOf[Seq[Mutant]])
    assertEquals(mutations.length, 1)
  }

  test("Can mutate with unsupported levels as option") {
    val mutations = WeaponRegeX.mutate("^a", mutationLevels = Seq(100, 1000)).getOrFail

    assert(mutations.isInstanceOf[Seq[Mutant]])
    assertEquals(mutations, Nil)
  }

  test("Can mutate with both mutators and levels as option") {
    val mutations = WeaponRegeX.mutate("^a", BuiltinMutators.all, Seq(1)).getOrFail

    assert(mutations.isInstanceOf[Seq[Mutant]])
    assertEquals(mutations.length, 1)
  }

  test("Returns an empty sequence if there are no mutants") {
    val mutations = WeaponRegeX.mutate("a").getOrFail

    assert(mutations.isInstanceOf[Seq[Mutant]])
    assertEquals(mutations, Nil)
  }

  test("Returns a Left with error message if the regex is invalid") {
    val mutations = WeaponRegeX.mutate("*(a|$]")

    assert(clue(mutations) match {
      case Left(msg) => msg.startsWith(ErrorMessage.parserErrorHeader)
      case _         => false
    })
  }
}
