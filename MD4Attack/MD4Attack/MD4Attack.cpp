// MD4Attack.cpp: определяет экспортированные функции для приложения DLL.
//

#include "stdafx.h"
#include "MD4WangAttack.h"
#include <cstdlib>

struct ArrayStruct {
	int values[32];
};

extern "C" __declspec(dllexport) int findCollisions(struct ArrayStruct **allocint)
{
	int* values = MD4Attack::MD4WangAttack::findCollisions();
	*allocint = (struct ArrayStruct *) malloc(sizeof(struct ArrayStruct));
	for (int i = 0; i < 32; i++)
	{
		(*allocint)->values[i] = values[i];
	}
	return 0;
}

extern "C" __declspec(dllexport) int releaseResources(struct ArrayStruct **ptr)
{
	struct ArrayStruct *p = *ptr;
	free(p);
	return 0;
}