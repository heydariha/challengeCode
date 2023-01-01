package com.hadi.kotlin.application

import com.hadi.kotlin.application.api.InsuredPersonService
import com.hadi.kotlin.config.NotFoundException
import com.hadi.kotlin.domain.InsuredPerson
import com.hadi.kotlin.domain.InsuredPersonDto
import com.hadi.kotlin.infrastructure.InsuredPersonRepository
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.assertTrue
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import java.util.*

const val MESSAGE = "Insured Person not found"

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class InsuredPersonServiceImplTest{

  @Autowired
  lateinit var insuredPersonService: InsuredPersonService

  @Autowired
  lateinit var insuredPersonRepository: InsuredPersonRepository

  lateinit var savedInsuredPerson: List<InsuredPerson>

  @BeforeAll
  fun createDefaultInsuredPersons(){
    val insuredPerson1 = insuredPersonRepository.save(createInsuredPersonTemplate())
    val insuredPerson2 = insuredPersonRepository.save(createInsuredPersonTemplate(firstName = "Peter", "Schmidt"))
    savedInsuredPerson = listOf(insuredPerson1, insuredPerson2)
  }

  @Test
  fun `getting insured person by valid uuid must return one model`(){
    //GIVEN
    val savedInsuredPerson = savedInsuredPerson.first()

    // WHEN
    val savedModel = insuredPersonService.getInsuredPerson(savedInsuredPerson.uuid)

    // THEN
    assertTrue(savedInsuredPerson.javaClass == savedModel.javaClass)
    assertTrue(savedInsuredPerson.uuid == savedModel.uuid)
    assertTrue(savedInsuredPerson.firstName == savedModel.firstName)
    assertTrue(savedInsuredPerson.secondName == savedModel.secondName)
    assertTrue(savedInsuredPerson.premium == savedModel.premium)
  }

  @Test
  fun `getting insured person with invalid uuid must throws an error`(){
    // WHEN
    val exception = Assertions.assertThrows(NotFoundException::class.java) {
      insuredPersonService.getInsuredPerson(UUID.randomUUID())
    }

    //THEN
    Assertions.assertEquals(MESSAGE, exception.message)
  }

  @Test
  fun `update existing insured person must be successful`(){
    //GIVEN
    val insuredPersons = listOf(InsuredPersonDto(
      uuid = savedInsuredPerson[0].uuid,
      firstName = "John",
      secondName = "Doe",
      premium = 11.00
    ), InsuredPersonDto(
      uuid = savedInsuredPerson[1].uuid,
      firstName = "Roger",
      secondName = "Water",
      premium = 12.20
    ))

    // WHEN
    val updatedList = insuredPersonService.assembleInsuredPersonForUpdate(insuredPersons)

    //THEN
    Assertions.assertEquals("John", updatedList[0].firstName)
    Assertions.assertEquals("Doe", updatedList[0].secondName)
    Assertions.assertEquals(11.00, updatedList[0].premium)
    Assertions.assertEquals("Roger", updatedList[1].firstName)
    Assertions.assertEquals("Water", updatedList[1].secondName)
    Assertions.assertEquals(12.20, updatedList[1].premium)
  }

  @Test
  fun `add new one to existing insured person must be successful`(){
    //GIVEN
    val insuredPersons = listOf(InsuredPersonDto(
      uuid = savedInsuredPerson[0].uuid,
      firstName = "John",
      secondName = "Doe",
      premium = 11.00
    ), InsuredPersonDto(
      uuid = savedInsuredPerson[1].uuid,
      firstName = "Roger",
      secondName = "Water",
      premium = 12.20
    ), InsuredPersonDto(
      uuid = null,
      firstName = "Andy",
      secondName = "Williams",
      premium = 15.20
    ))

    // WHEN
    val updatedList = insuredPersonService.assembleInsuredPersonForUpdate(insuredPersons)

    //THEN
    Assertions.assertEquals("John", updatedList[0].firstName)
    Assertions.assertEquals("Doe", updatedList[0].secondName)
    Assertions.assertEquals(11.00, updatedList[0].premium)
    Assertions.assertEquals("Roger", updatedList[1].firstName)
    Assertions.assertEquals("Water", updatedList[1].secondName)
    Assertions.assertEquals(12.20, updatedList[1].premium)
    Assertions.assertEquals("Andy", updatedList[2].firstName)
    Assertions.assertEquals("Williams", updatedList[2].secondName)
    Assertions.assertEquals(15.20, updatedList[2].premium)
  }

  @Test
  fun `remove existing insured person must be successful`(){
    //GIVEN
    val insuredPersons = listOf(InsuredPersonDto(
      uuid = savedInsuredPerson[0].uuid,
      firstName = "John",
      secondName = "Doe",
      premium = 11.00
    ))

    // WHEN
    val updatedList = insuredPersonService.assembleInsuredPersonForUpdate(insuredPersons)

    //THEN
    Assertions.assertEquals("John", updatedList[0].firstName)
    Assertions.assertEquals("Doe", updatedList[0].secondName)
    Assertions.assertEquals(11.00, updatedList[0].premium)
    val oldInsuredPerson = updatedList.filter { it.firstName == "Roger" || it.firstName == "Andy" }
    Assertions.assertEquals(0, oldInsuredPerson.size)
  }
}
