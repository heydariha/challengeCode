package com.embea.kotlin.domain

import org.hibernate.annotations.Type
import java.io.Serializable
import java.math.BigDecimal
import java.time.LocalDate
import java.util.*
import javax.persistence.*
import javax.validation.constraints.NotNull


@Entity
data class Policy(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) var id: Long,
    @Type(type = "uuid-char")
    @Column(
        nullable = false,
        unique = true,
        updatable = false,
        columnDefinition = "CHAR(36)"
    ) var uuid: UUID = UUID.randomUUID(),
    var startDate: LocalDate,
    @OneToMany(
        cascade = [(CascadeType.ALL)],
        fetch = FetchType.EAGER
    )
    @JoinColumn(name = "policy")
    var insuredPersons: List<InsuredPerson>
) : Serializable {

    override fun toString(): String {
        return "Policy(id=$id, uuid='$uuid', startDate='$startDate', insuredPersons=$insuredPersons)"
    }

    fun addToInsuredPerson(unmappedInsuredPerson: List<InsuredPerson>?) {
        unmappedInsuredPerson?.let {
            unmappedInsuredPerson.forEach {
                it.policy = this
            }
            insuredPersons = unmappedInsuredPerson
        }
    }
}

@Entity
data class InsuredPerson(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) var id: Long,
    val firstName: String,
    val secondName: String,
    val premium: BigDecimal,
    @ManyToOne
    @NotNull var policy: Policy?
) : Serializable {
    override fun toString(): String {
        return "InsuredPerson(id=$id, firstName='$firstName', secondName='$secondName', premium=$premium, policyId=${policy?.id})"
    }
}

